package com.theus.health.base.service.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.theus.health.base.model.dto.system.deptOrgan.DeptOrganAddDTO;
import com.theus.health.base.model.dto.system.deptOrgan.DeptOrganUpdateDTO;
import com.theus.health.base.model.dto.system.deptOrgan.FindDeptOrganDTO;
import com.theus.health.base.model.po.system.SysDeptCenter;
import com.theus.health.base.model.po.system.SysDeptOrgan;

/**
 * @author libin
 * @date 2019-11-4 17:54
 */
public interface SysDeptOrganService extends IService<SysDeptOrgan> {
    /**
     * 根据院内科室ID查找科室
     * @param deptId 院内科室id
     * @param organId 机构id
     * @return 院内科室信息
     */
    SysDeptOrgan findById(String deptId,String organId);

    /**
     * 根据院内科室名称查找科室
     * @param deptName 院内科室名称
     * @param organId 机构id
     * @return 院内科室信息
     */
    SysDeptOrgan findByName(String deptName,String organId);

    /**
     * 获取院内科室列表
     * @param findDeptOrganDTO
     * @return
     */
    IPage<SysDeptOrgan> findPage(FindDeptOrganDTO findDeptOrganDTO);

    /**
     * 新增院内科室
     * @param deptOrganAddDTO
     */
    void add(DeptOrganAddDTO deptOrganAddDTO);

    /**
     * 修改院内科室
     * @param deptOrganUpdateDTO
     */
    void update(DeptOrganUpdateDTO deptOrganUpdateDTO);
}
