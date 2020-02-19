package com.theus.health.base.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theus.health.base.model.dto.system.deptCenter.DeptCenterAddDTO;
import com.theus.health.base.model.dto.system.deptCenter.DeptCenterUpdateDTO;
import com.theus.health.base.model.dto.system.deptCenter.FindDeptCenterDTO;
import com.theus.health.base.model.po.system.SysDeptCenter;

import java.util.List;

/**
 * @author libin
 * @date 2019-10-24 20:51
 */
public interface SysDeptCenterService extends IService<SysDeptCenter> {
    /**
     * 根据中心科室ID查找科室
     * @param id 中心科室id
     * @return 中心科室信息
     */
    SysDeptCenter findById(String id);

    /**
     * 根据中心科室名称查找科室
     * @param name 中心科室名称
     * @return 中心科室信息
     */
    SysDeptCenter findByName(String name);
    /**
     * 查询中心科室列表
     * @param findDictDTO
     * @return
     */
    List<SysDeptCenter> findTree(FindDeptCenterDTO findDictDTO);

    /**
     * 新增中心科室
     * @param deptCenterAddDTO
     */
    void add(DeptCenterAddDTO deptCenterAddDTO);

    /**
     * 修改中心科室
     * @param deptCenterUpdateDTO
     */
    void update(DeptCenterUpdateDTO deptCenterUpdateDTO);
}
