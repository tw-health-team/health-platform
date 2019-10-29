package com.theus.health.base.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theus.health.base.model.dto.system.organ.OrganAddDTO;
import com.theus.health.base.model.dto.system.organ.OrganUpdateDTO;
import com.theus.health.base.model.dto.system.organ.FindOrganDTO;
import com.theus.health.base.model.po.system.SysOrgan;

import java.util.List;

/**
 * @author tangwei
 * @date 2019-09-25 20:19
 */
public interface SysOrganService extends IService<SysOrgan> {

    /**
     * 根据机构ID查找机构
     * @param name 机构名
     * @return 机构信息
     */
    SysOrgan findById(String name);

    /**
     * 根据机构名称查找机构
     * @param name 机构名
     * @return 机构信息
     */
    SysOrgan findByName(String name);

    /**
     * 添加机构
     * @param addDTO 机构数据DTO
     */
    void add(OrganAddDTO addDTO);

    /**
     * 更新机构数据
     * @param id 机构id
     * @param updateDTO 机构数据DTO
     */
    void update(String id, OrganUpdateDTO updateDTO);

    /**
     * 查询机构树
     * @param findDeptDTO 机构查询条件
     * @return 机构树
     */
    List<SysOrgan> findTree(FindOrganDTO findDeptDTO);

}
