package com.theus.health.base.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theus.health.base.model.po.system.SysResource;
import com.theus.health.base.model.po.system.SysRoleResource;
import com.theus.health.base.mapper.system.SysRolePermissionMapper;
import com.theus.health.base.service.system.SysResourceService;
import com.theus.health.base.service.system.SysRoleResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tangwei
 * @date 2019-07-24 19:34
 */
@Service
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRoleResource>
        implements SysRoleResourceService {

    @Resource
    private SysResourceService resourceService;

    @Override
    public List<SysResource> findAllResourceByRoleId(String rid) {
        List<SysRoleResource> rps = this.list(new QueryWrapper<SysRoleResource>().eq("role_id",rid));
        if(rps!=null){
            List<String> pids = new ArrayList<>();
            rps.forEach(v->pids.add(v.getResourceId()));
            if(pids.size()==0){
                return null;
            }
            return resourceService.list(new QueryWrapper<SysResource>()
                    .in("id", pids)
                    .orderByAsc("sort")
            );
        }
        return null;
    }

    @Override
    public void saveRoleResource(List<SysRoleResource> records) {

    }
}
