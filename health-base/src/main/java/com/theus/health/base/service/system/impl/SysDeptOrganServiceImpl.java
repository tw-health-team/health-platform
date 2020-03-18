package com.theus.health.base.service.system.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theus.health.base.mapper.system.SysDeptOrganMapper;
import com.theus.health.base.model.dto.system.deptOrgan.DeptOrganAddDTO;
import com.theus.health.base.model.dto.system.deptOrgan.DeptOrganUpdateDTO;
import com.theus.health.base.model.dto.system.deptOrgan.FindDeptOrganDTO;
import com.theus.health.base.model.po.system.SysDeptOrgan;
import com.theus.health.base.service.system.SysDeptOrganService;
import com.theus.health.base.util.ShiroUtils;
import com.theus.health.core.exception.BusinessException;
import com.theus.health.core.util.ChinesePinyinUtil;
import com.theus.health.core.util.WuBiUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author libin
 * @date 2019-11-4 17:50
 */
@Service
public class SysDeptOrganServiceImpl extends ServiceImpl<SysDeptOrganMapper, SysDeptOrgan> implements SysDeptOrganService {

    @Override
    public SysDeptOrgan findById(String deptId,String organId) {
        return this.getOne(new QueryWrapper<SysDeptOrgan>().eq("dept_id", deptId).eq("organ_id", organId));
    }

    @Override
    public SysDeptOrgan findByName(String deptName,String organId) {
        return this.getOne(new QueryWrapper<SysDeptOrgan>().eq("dept_name", deptName).eq("organ_id", organId));
    }

    @Override
    public void add(DeptOrganAddDTO addDTO) {
        SysDeptOrgan findDept = this.findById(addDTO.getId(),addDTO.getOrganId());
        if (findDept != null) {
            throw BusinessException.fail(String.format("同一机构内已经存在ID为 %s 的院内科室", addDTO.getId()));
        }
        findDept = this.findByName(addDTO.getDeptName(),addDTO.getOrganId());
        if (findDept != null) {
            throw BusinessException.fail(String.format("同一机构内已经存在院内科室名称为 %s 的科室", addDTO.getDeptName()));
        }
        findDept = new SysDeptOrgan();
        BeanUtils.copyProperties(addDTO, findDept);
        // 获取中心科室名称拼音码
        findDept.setSimpleSpelling(ChinesePinyinUtil.getPinYinHeadChar(findDept.getDeptName()));
        // 获取名称的五笔码
        findDept.setSimpleWubi(WuBiUtil.getWubiHeadChar(findDept.getDeptName()));
        findDept.setCreateTime(new Date());

        // 赋值新增固定字段
        ShiroUtils.setInsert(findDept);

        this.save(findDept);
    }

    @Override
    public void update(DeptOrganUpdateDTO updateDTO) {
        SysDeptOrgan deptCenter = this.getById(updateDTO.getId());
        if (deptCenter == null) {
            throw BusinessException.fail(
                    String.format("更新失败，不存在ID为 %s 的院内科室", updateDTO.getId()));
        }
        SysDeptOrgan findDept = this.getOne(new QueryWrapper<SysDeptOrgan>()
                .eq("dept_name", updateDTO.getDeptName()).eq("dept_id", updateDTO.getDeptId()).eq("organ_id", updateDTO.getOrganId()).ne("id", updateDTO.getId()));
        if (findDept != null) {
            throw BusinessException.fail(
                    String.format("更新失败，已经存在院内科室名为 %s 的院内科室", updateDTO.getDeptName()));
        }
        // 名称改变 重新生成拼音码
        if(!deptCenter.getDeptName().equals(updateDTO.getDeptName())){
            // 获取机构名称的拼音首字母
            deptCenter.setSimpleSpelling(ChinesePinyinUtil.getPinYinHeadChar(updateDTO.getDeptName()));
            deptCenter.setSimpleWubi(WuBiUtil.getWubiHeadChar(updateDTO.getDeptName()));
        }
        BeanUtils.copyProperties(updateDTO, deptCenter);
        // 赋值修改固定字段
        ShiroUtils.setUpdate(deptCenter);
        try {
            this.updateById(deptCenter);
        } catch (BusinessException e) {
            throw BusinessException.fail(e.getMsg(), e);
        } catch (Exception e) {
            throw BusinessException.fail("院内科室信息更新失败", e);
        }
    }

    /**
     * 查询院内科室分页列表
     * @param findDeptOrganDTO 查询条件
     * @return page 分页数据
     */
    @Override
    public IPage<SysDeptOrgan> findPage(FindDeptOrganDTO findDeptOrganDTO) {
        IPage<SysDeptOrgan> deptOrganPagePage = new Page<>(findDeptOrganDTO.getPageNum(),
                findDeptOrganDTO.getPageSize());

        deptOrganPagePage.setRecords(this.baseMapper.findPage(deptOrganPagePage,findDeptOrganDTO));
        return deptOrganPagePage;
    }
}
