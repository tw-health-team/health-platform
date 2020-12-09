package com.theus.health.record.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theus.health.base.common.service.QueryService;
import com.theus.health.record.model.dto.FindPersonalInfoDTO;
import com.theus.health.record.model.dto.FindPersonalListDTO;
import com.theus.health.record.model.dto.PersonalInfoDTO;
import com.theus.health.record.model.po.EhrPersonalInfo;

import java.util.List;

/**
 * @author tangwei
 * @date 2020-03-25 15:06
 */
public interface PersonalInfoService extends IService<EhrPersonalInfo>, QueryService<PersonalInfoDTO,FindPersonalListDTO> {

    /**
     * 获取居民个人基本信息
     * @param findPersonalInfoDTO 查询条件
     * @return 个人基本信息
     */
    PersonalInfoDTO getRecord(FindPersonalInfoDTO findPersonalInfoDTO);

    /**
     * 获取居民个人基本信息
     * @param idCard 身份证号
     * @return 个人基本信息
     */
    PersonalInfoDTO selectByIdCard(String idCard);

    /**
     * 添加个人基本信息
     * @param personalInfoDTO 个人基本信息
     */
    void addPersonalInfo(PersonalInfoDTO personalInfoDTO);

    /**
     * 修改个人基本信息
     * @param personalInfoDTO 个人基本信息
     */
    void updatePersonalInfo(PersonalInfoDTO personalInfoDTO);
}
