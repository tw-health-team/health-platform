package com.theus.health.record.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theus.health.base.common.constants.DictClassCode;
import com.theus.health.base.service.system.SysDictService;
import com.theus.health.base.util.ShiroUtils;
import com.theus.health.core.exception.BusinessException;
import com.theus.health.record.mapper.PersonalInfoMapper;
import com.theus.health.record.model.dto.FindPersonalInfoDTO;
import com.theus.health.record.model.dto.FindPersonalListDTO;
import com.theus.health.record.model.dto.PersonalInfoDTO;
import com.theus.health.record.model.po.EhrPersonalInfo;
import com.theus.health.record.service.PersonalInfoService;
import com.theus.health.record.util.PersonalInfoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tangwei
 * @date 2020-03-25 15:06
 */
@Service
public class PersonalInfoServiceImpl extends ServiceImpl<PersonalInfoMapper, EhrPersonalInfo> implements PersonalInfoService {
    @Resource
    private PersonalInfoMapper personalInfoMapper;
    @Resource
    private SysDictService dictService;

    @Override
    public IPage<PersonalInfoDTO> list(FindPersonalListDTO query) {
        IPage<EhrPersonalInfo> page = new Page<>(query.getPageNum(), query.getPageSize());
        page.setRecords(this.personalInfoMapper.findPage(page, query));
        IPage<PersonalInfoDTO> result = new Page<>();
        BeanUtils.copyProperties(page, result);
        List<PersonalInfoDTO> list = new ArrayList<>();
        page.getRecords().forEach(v -> {
            PersonalInfoDTO personalInfoDTO = new PersonalInfoDTO();
            BeanUtils.copyProperties(v, personalInfoDTO);
            // 计算周岁
            personalInfoDTO.setAge(DateUtil.ageOfNow(personalInfoDTO.getBirthday()));
            personalInfoDTO.setGender(dictService.getItemName(DictClassCode.PersonalInfo.GENDER, personalInfoDTO.getGender()));
            list.add(personalInfoDTO);
        });
        result.setRecords(list);
        return result;
    }

    @Override
    public PersonalInfoDTO getRecord(FindPersonalInfoDTO findPersonalInfoDTO) {
        PersonalInfoDTO personalInfoDTO = new PersonalInfoDTO();
        String personalId = findPersonalInfoDTO.getPersonalId();
        String idCard = findPersonalInfoDTO.getIdCard();
        if (StrUtil.isNotBlank(personalId)) {
            // 通过个人档案标识号获取档案
            EhrPersonalInfo ehrPersonalInfo = this.baseMapper.selectById(personalId);
            BeanUtils.copyProperties(ehrPersonalInfo, personalInfoDTO);
        } else if (StrUtil.isNotBlank(idCard)) {
            // 通过身份证号获取档案
            personalInfoDTO = this.selectByIdCard(idCard);
        }
        return personalInfoDTO;
    }

    @Override
    public PersonalInfoDTO selectByIdCard(String idCard) {
        PersonalInfoDTO personalInfoDTO = new PersonalInfoDTO();
        List<EhrPersonalInfo> personalInfoList = this.getPersonalInfo(idCard, "", "");
        if (personalInfoList != null && personalInfoList.size() > 0) {
            BeanUtils.copyProperties(personalInfoList.get(0), personalInfoDTO);
        } else {
            personalInfoDTO = null;
        }
        return personalInfoDTO;
    }

    /**
     * 根据身份证查询基本信息
     *
     * @param idCard   身份证
     * @param cardType 证件类型
     * @param cardNum  证件号码
     * @return 基本信息
     */
    private List<EhrPersonalInfo> getPersonalInfo(String idCard, String cardType, String cardNum) {
        if (StrUtil.isNotBlank(idCard)) {
            return this.list(new QueryWrapper<EhrPersonalInfo>()
                    .eq("id_card", idCard)
                    .orderByDesc("create_time"));
        } else {
            return this.list(new QueryWrapper<EhrPersonalInfo>()
                    .eq("card_type", cardType)
                    .eq("card_num", cardNum)
                    .orderByDesc("create_time"));
        }
    }

    @Override
    public void addPersonalInfo(PersonalInfoDTO personalInfoDTO) {
        EhrPersonalInfo ehrPersonalInfo = new EhrPersonalInfo();
        BeanUtils.copyProperties(personalInfoDTO, ehrPersonalInfo);
        // 完善基本信息
        PersonalInfoUtil.completeInfo(ehrPersonalInfo);
        String idCard = ehrPersonalInfo.getIdCard();
        String cardNum = ehrPersonalInfo.getCardNum();
        String cardType = ehrPersonalInfo.getCardType();
        // 判断档案是否存在
        this.judgePersonalInfo(idCard, cardType, cardNum);
        // 赋值新增固定字段
        ShiroUtils.setInsert(ehrPersonalInfo);
        // 新增档案
        this.baseMapper.insert(ehrPersonalInfo);
    }

    @Override
    public void updatePersonalInfo(PersonalInfoDTO personalInfoDTO) {
        EhrPersonalInfo ehrPersonalInfo = this.baseMapper.selectById(personalInfoDTO.getPersonalId());
        if (ehrPersonalInfo == null) {
            throw BusinessException.fail("需要更新的档案不存在！");
        }
        String idCard = personalInfoDTO.getIdCard();
        String cardNum = personalInfoDTO.getCardNum();
        String cardType = personalInfoDTO.getCardType();
        // 证件号码或证件类型是否变更
        boolean isUpdate = StrUtil.isNotBlank(cardNum) && !ehrPersonalInfo.getCardNum().equals(cardNum);
        isUpdate = isUpdate || StrUtil.isNotBlank(cardType) && !ehrPersonalInfo.getCardType().equals(cardType);
        if (StrUtil.isNotBlank(idCard) && !ehrPersonalInfo.getIdCard().equals(idCard)) {
            // 身份证号变更
            this.judgePersonalInfo(idCard, "", "");
        } else if (isUpdate) {
            // 证件类型或证件号码变更
            this.judgePersonalInfo("", cardNum, cardType);
        }
        BeanUtils.copyProperties(personalInfoDTO, ehrPersonalInfo);
        // 完善基本信息
        PersonalInfoUtil.completeInfo(ehrPersonalInfo);
        // 赋值修改固定字段
        ShiroUtils.setUpdate(ehrPersonalInfo);
        // 更新档案
        this.baseMapper.updateById(ehrPersonalInfo);
    }

    /**
     * 判断个人基本信息是否存在
     *
     * @param idCard   身份证号
     * @param cardType 证件类型
     * @param cardNum  证件号码
     */
    private void judgePersonalInfo(String idCard, String cardType, String cardNum) {
        List<EhrPersonalInfo> list = null;
        if (StrUtil.isNotBlank(idCard)) {
            list = this.getPersonalInfo(idCard, "", "");
            if (list != null && list.size() > 0) {
                throw BusinessException.fail("已存在身份证号【" + idCard + "】的档案！");
            }
        } else if (StrUtil.isNotBlank(cardNum)) {
            list = this.getPersonalInfo("", cardType, cardNum);
            if (list != null && list.size() > 0) {
                throw BusinessException.fail("已存在证件号码【" + cardNum + "】的档案！");
            }
        }
    }
}
