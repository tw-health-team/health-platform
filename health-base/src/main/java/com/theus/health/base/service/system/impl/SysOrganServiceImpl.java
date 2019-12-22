package com.theus.health.base.service.system.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theus.health.base.common.constants.SysConstants;
import com.theus.health.base.mapper.system.SysOrganMapper;
import com.theus.health.base.model.dto.system.organ.OrganAddDTO;
import com.theus.health.base.model.dto.system.organ.OrganUpdateDTO;
import com.theus.health.base.model.dto.system.organ.FindOrganDTO;
import com.theus.health.base.model.po.system.SysOrgan;
import com.theus.health.base.service.global.impl.BaseServiceImpl;
import com.theus.health.base.service.system.SysOrganService;
import com.theus.health.base.util.ShiroUtils;
import com.theus.health.core.exception.BusinessException;
import com.theus.health.core.util.ChinesePinyinUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author tangwei
 * @date 2019-09-25 20:19
 */
@Service
public class SysOrganServiceImpl extends ServiceImpl<SysOrganMapper, SysOrgan> implements SysOrganService {

    @Override
    public SysOrgan findById(String id) {
        return this.getOne(new QueryWrapper<SysOrgan>().eq("id", id));
    }

    @Override
    public SysOrgan findByName(String name) {
        return this.getOne(new QueryWrapper<SysOrgan>().eq("name", name));
    }

    @Override
    public void add(OrganAddDTO addDTO) {
        SysOrgan findDept = this.findById(addDTO.getId());
        if (findDept != null) {
            throw BusinessException.fail(String.format("已经存在机构ID为 %s 的机构", addDTO.getId()));
        }
        findDept = this.findByName(addDTO.getName());
        if (findDept != null) {
            throw BusinessException.fail(String.format("已经存在机构名为 %s 的机构", addDTO.getName()));
        }
        findDept = new SysOrgan();
        BeanUtils.copyProperties(addDTO, findDept);
        // 上级机构为空 则赋默认值
        if(StrUtil.isBlank(findDept.getParentId())) {
            findDept.setParentId(SysConstants.TOP_DEPT_CODE);
        }
        // 获取机构名称的拼音首字母
        findDept.setSimpleSpelling(ChinesePinyinUtil.getPinYinHeadChar(findDept.getName()));
        // 赋值新增固定字段
        ShiroUtils.setInsert(findDept);
        this.save(findDept);
    }

    @Override
    public void update(String id, OrganUpdateDTO updateDTO) {
        SysOrgan organ = this.getById(id);
        if (organ == null) {
            throw BusinessException.fail(
                    String.format("更新失败，不存在ID为 %s 的机构", id));
        }
        // 名称改变 重新生成拼音码
        if(!organ.getName().equals(updateDTO.getName())){
            // 获取机构名称的拼音首字母
            organ.setSimpleSpelling(ChinesePinyinUtil.getPinYinHeadChar(updateDTO.getName()));
        }
        SysOrgan findDept = this.getOne(new QueryWrapper<SysOrgan>()
                .eq("name", updateDTO.getName()).ne("id", id));
        if (findDept != null) {
            throw BusinessException.fail(
                    String.format("更新失败，已经存在机构名为 %s 的机构", updateDTO.getName()));
        }
        BeanUtils.copyProperties(updateDTO, organ);
        // 赋值修改固定字段
        ShiroUtils.setUpdate(organ);
        try {
            this.updateById(organ);
        } catch (BusinessException e) {
            throw BusinessException.fail(e.getMsg(), e);
        } catch (Exception e) {
            throw BusinessException.fail("机构信息更新失败", e);
        }
    }

    @Override
    public List<SysOrgan> findTree(FindOrganDTO findOrganDTO) {
        List<SysOrgan> sysOrgans = new ArrayList<>();
        String name = findOrganDTO.getName();
        List<SysOrgan> sysOrganList;
        // 查询条件为空 直接查询所有机构数据后生成树形结构
        if (StrUtil.isBlank(name)) {
            sysOrganList = this.baseMapper.list(findOrganDTO);
            sysOrgans = initTree(sysOrganList);
        } else {
            // 根据条件查询机构
            sysOrganList = this.baseMapper.list(findOrganDTO);
            // 循环机构列表
            for (SysOrgan organ : sysOrganList) {
                // 不是顶级机构 则获取上级机构的名称
//                if (StrUtil.isNotBlank(organ.getParentId()) && !"0".equals(organ.getParentId())) {
//                    SysOrgan sysOrgan = this.getOne(new QueryWrapper<SysOrgan>()
//                            .eq("id", organ.getParentId()).select("name"));
//                    organ.setParentName(sysOrgan.getName());
//                }
                // 将所有机构置为第一层
                organ.setTreeLayer(0);
                sysOrgans.add(organ);
            }
            // 根据机构列表生成机构树
            findChildren(sysOrgans, new ArrayList<>(sysOrgans));
            // 去除重复的机构
            sysOrgans = removeDuplicate(sysOrganList);
        }
        return sysOrgans;
    }

    /**
     * 根据机构列表生成机构树
     * @param organList 机构列表
     * @return 机构树
     */
    private List<SysOrgan> initTree(List<SysOrgan> organList){
        List<SysOrgan> sysDepts = new ArrayList<>();
        if (organList != null) {
            // 循环机构列表
            for (SysOrgan organ : organList) {
                // 保存顶级机构
                if (SysConstants.TOP_DEPT_CODE.equals(organ.getParentId())) {
                    organ.setTreeLayer(0);
                    sysDepts.add(organ);
                }
            }
            // order_num升序
            organList.sort(Comparator.comparing(SysOrgan::getOrderNum));
            // 根据顶级机构和机构列表生成机构树
            findChildren(sysDepts, organList);
        }
        return sysDepts;
    }

    /**
     * 根据上级机构和机构列表生成机构树
     * @param sysOrgans 上级机构list
     * @param organs 机构列表
     */
    private void findChildren(List<SysOrgan> sysOrgans, List<SysOrgan> organs) {
        // 循环上级机构
        for (SysOrgan sysOrgan : sysOrgans) {
            List<SysOrgan> children = new ArrayList<>();
            // 循环机构列表
            for (SysOrgan organ : organs) {
                // 将机构列表中的父id是上级机构的id的机构存入子机构列表
                if (sysOrgan.getId().equals(organ.getParentId())) {
                    organ.setParentName(sysOrgan.getName());
                    organ.setTreeLayer(sysOrgan.getTreeLayer() + 1);
                    children.add(organ);
                }
            }
            // order_num升序
            children.sort(Comparator.comparing(SysOrgan::getOrderNum));
            sysOrgan.setChildren(children);
            findChildren(children, organs);
        }
    }

    /**
     * 获取机构的下级机构信息
     *
     * @param sysOrgan 机构信息
     */
    private void findChildren(SysOrgan sysOrgan){
        List<SysOrgan> sysOrgans = this.list(new QueryWrapper<SysOrgan>()
                .eq("parent_id", sysOrgan.getId())
                .orderByAsc("order_num"));
        sysOrgans.forEach(v-> v.setParentName(sysOrgan.getName()));
        sysOrgan.setChildren(sysOrgans);
        // 循环机构列表
        for (SysOrgan organ : sysOrgans) {
            findChildren(organ);
        }
    }

    private List<SysOrgan> removeDuplicate(List<SysOrgan> organList){
        List<SysOrgan> sysDepts = new ArrayList<>();
        // 循环机构
        for (SysOrgan organ : organList) {
            boolean isExists = false;
            // 循环其他机构，判断是否存在于其他机构的子机构中
            for (SysOrgan otherDept : organList) {
                if(!organ.getId().equals(otherDept.getId())){
                    if(isExistsInList(organ,otherDept.getChildren())){
                        isExists = true;
                        break;
                    }
                }
            }
            // 若不存在与其他机构的子机构中则保留
            if(!isExists){
                sysDepts.add(organ);
            }
        }
        return sysDepts;
    }

    /**
     * 是否存在于列表
     * @param sysDept 实体
     * @param organList 实体list
     * @return 是否
     */
    private boolean isExistsInList(SysOrgan sysDept, List<SysOrgan> organList) {
        boolean isExists = false;
        if (organList != null) {
            for (SysOrgan otherDept : organList) {
                if (sysDept.getId().equals(otherDept.getId())) {
                    isExists = true;
                    break;
                }
                if (!isExists) {
                    isExists = isExistsInList(sysDept, otherDept.getChildren());
                }
            }
        }
        return isExists;
    }
}
