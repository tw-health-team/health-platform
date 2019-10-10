package com.theus.health.base.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theus.health.base.model.po.system.SysResource;
import com.theus.health.base.model.po.system.SysRoleResource;

import java.util.List;

/**
 * @author tangwei
 * @date 2019-07-24 19:33
 */
public interface SysRoleResourceService extends IService<SysRoleResource> {

    /**
     * 根据角色id获取对应资源权限
     *
     * @param rid 角色id
     * @return 资源权限
     */
    List<SysResource> findAllResourceByRoleId(String rid);

    /**
     * 保存角色资源
     * @param records 资源
     */
    void saveRoleResource(List<SysRoleResource> records);
}
