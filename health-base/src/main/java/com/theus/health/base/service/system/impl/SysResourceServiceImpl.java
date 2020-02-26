package com.theus.health.base.service.system.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theus.health.base.common.constants.ResourceConstants;
import com.theus.health.base.common.constants.SysConstants;
import com.theus.health.base.model.dto.system.resource.ResourceDTO;
import com.theus.health.base.model.po.system.SysResource;
import com.theus.health.base.mapper.system.SysResourceMapper;
import com.theus.health.base.service.global.ShiroService;
import com.theus.health.base.service.system.SysResourceService;
import com.theus.health.core.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author tangwei
 * @date 2019-07-24 19:38
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource>
        implements SysResourceService {

    @Resource
    private ShiroService shiroService;
    @Resource
    private  SysResourceMapper sysResourceMapper;

    @Override
    public List<SysResource> treeList() {
        QueryWrapper<SysResource> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", "0")
                .or()
                .isNull("parent_id")
                .orderByAsc("sort");
        // 获取资源
        List<SysResource> resources = this.list(wrapper);
        // 遍历获取子集
        if (resources != null && resources.size() > 0) {
            resources.forEach(this::findAllChild);
        }
        return resources;
    }

    @Override
    public void add(ResourceDTO dto) {
        if(ResourceConstants.Type.MENU.ordinal() == dto.getType()){
            if(StrUtil.isBlank(dto.getUrl())){
                throw BusinessException.fail("菜单路由不能为空！");
            }
        }
        SysResource resource = new SysResource();
        BeanUtils.copyProperties(dto, resource);
        resource.setCreateDate(new Date());
        this.save(resource);
        shiroService.reloadPerms();
    }

    @Override
    public void update(String id, ResourceDTO dto) {
        if(ResourceConstants.Type.MENU.ordinal() == dto.getType()){
            if(StrUtil.isBlank(dto.getUrl())){
                throw BusinessException.fail("菜单路由不能为空！");
            }
        }
        SysResource resource = this.getById(id);
        if (resource == null) {
            throw BusinessException.fail("更新失败，不存在ID为" + id + "的资源");
        }
        BeanUtils.copyProperties(dto, resource);
        this.updateById(resource);
        shiroService.reloadPerms();
    }

    @Override
    public void remove(String id) {
        SysResource resource = this.getOne(new QueryWrapper<SysResource>()
                .eq("id", id).select("id"));
        if (resource == null) {
            throw BusinessException.fail("删除失败，不存在ID为" + id + "的资源");
        }
        this.removeById(id);
        shiroService.reloadPerms();
    }

    @Override
    public void findAllChild(SysResource resource) {
        QueryWrapper<SysResource> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", resource.getId()).orderByAsc("sort");
        List<SysResource> resources = this.list(wrapper);
        resource.setChildren(resources);
        if (resources != null && resources.size() > 0) {
            resources.forEach(this::findAllChild);
        }
    }

    @Override
    public SysResource getResourceAllParent(SysResource resource, Map<String, SysResource> cacheMap,
                                            Map<String, SysResource> cacheMap2) {
        if (resource.getParentId() != null && !"".equals(resource.getParentId().trim())) {
            SysResource cacheParent = cacheMap.get(resource.getParentId());
            SysResource parent;
            if (cacheParent != null) {
                parent = cacheParent;
            } else {
                cacheParent = cacheMap2.get(resource.getParentId());
                if (cacheParent != null) {
                    parent = cacheParent;
                } else {
                    parent = this.getById(resource.getParentId());
                }
            }
            if (parent != null) {
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                //判断是否已经包含此对象
                if (!parent.getChildren().contains(resource)) {
                    parent.getChildren().add(resource);
                }
                cacheMap.put(resource.getParentId(), parent);
                //如果此父级还有父级，继续递归查询
                if (parent.getParentId() != null && !"".equals(parent.getParentId())) {
                    return getResourceAllParent(parent, cacheMap, cacheMap2);
                } else {
                    return parent;
                }
            }
        }
        return resource;
    }

    @Override
    public List<SysResource> findTree(String userName, int menuType) {
        List<SysResource> resourcesTree = new ArrayList<>();
        List<SysResource> resources = findByUser(userName);
        for (SysResource resource : resources) {
            if (resource.getParentId() == null || "0".equals(resource.getParentId())) {
                resource.setLevel(0);
                resourcesTree.add(resource);
            }
        }
        // resources.sort((o1, o2) -> o1.getSort().compareTo(o2.getSort()));
        findChildren(resourcesTree, resources, menuType);
        return resourcesTree;
    }

    @Override
    public List<SysResource> findByUser(String username) {
        List<SysResource> resources;
        if(username == null || "".equals(username) || SysConstants.SUPER_ADMIN.equalsIgnoreCase(username)) {
            QueryWrapper<SysResource> wrapper = new QueryWrapper<>();
            wrapper.orderByAsc("sort");
            // 获取资源
            resources = this.list(wrapper);
        }else{
            resources = sysResourceMapper.findByUserName(username);
        }
        return resources;
    }

    private void findChildren(List<SysResource> resourcesTree, List<SysResource> resources, int menuType) {
        for (SysResource sysResource : resourcesTree) {
            List<SysResource> children = new ArrayList<>();
            for (SysResource resource : resources) {
                if(menuType == 1 && resource.getType() == 2) {
                    // 如果是获取类型不需要按钮，且菜单类型是按钮的，直接过滤掉
                    continue ;
                }
                if (sysResource.getId() != null && sysResource.getId().equals(resource.getParentId())) {
                    resource.setParentName(sysResource.getName());
                    resource.setLevel(sysResource.getLevel() + 1);
                    children.add(resource);
                }
            }
            sysResource.setChildren(children);
            children.sort(Comparator.comparing(SysResource::getSort));
            findChildren(children, resources, menuType);
        }
    }
}
