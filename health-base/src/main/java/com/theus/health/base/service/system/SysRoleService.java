package com.theus.health.base.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theus.health.base.common.service.BaseService;
import com.theus.health.base.model.po.system.SysResource;
import com.theus.health.base.model.po.system.SysRole;
import com.theus.health.base.model.dto.system.role.FindRoleDTO;
import com.theus.health.base.model.dto.system.role.RoleAddDTO;
import com.theus.health.base.model.dto.system.role.RoleUpdateDTO;
import com.theus.health.base.model.po.system.SysRoleResource;

import java.util.List;

/**
 * @author tangwei
 * @date 2019-07-23 23:21
 */
public interface SysRoleService extends IService<SysRole>,
        BaseService<SysRole, RoleAddDTO, RoleUpdateDTO,String, FindRoleDTO> {
    /**
     * 获取指定ID用户的所有角色
     * @param uid 用户ID
     * @param hasResource 是否包含所有的角色权限
     * @return 角色集合
     */
    List<SysRole> findAllRoleByUserId(String uid, Boolean hasResource);

    /**
     * 获取超级管理员角色
     * @return 角色信息
     */
    SysRole getSuperRole();

    /**
     * 更新缓存
     * @param role 角色
     * @param author 是否清空授权信息
     * @param out 是否清空session
     */
    void updateCache(SysRole role,Boolean author, Boolean out);

    /**
     * 查询角色资源集合
     * @param roleId 角色id
     * @return 资源list
     */
    List<SysResource> findRoleResource(String roleId);

    /**
     * 保存角色资源
     * @param records 资源
     */
    void saveRoleResource(List<SysRoleResource> records);
}
