package com.theus.health.base.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theus.health.base.common.constants.ResourceConstants;
import com.theus.health.base.model.dto.system.resource.ResourceDTO;
import com.theus.health.base.model.po.system.SysResource;

import java.util.List;
import java.util.Map;

/**
 * @author tangwei
 * @date 2019-07-24 19:38
 */
public interface SysResourceService extends IService<SysResource> {

    /**
     * 获取资源列表
     * @return 资源列表
     */
    List<SysResource> findAllResource();

    /**
     * 获取资源树
     * @return 资源树
     */
    List<SysResource> treeList();

    /**
     * 添加资源
     * @param dto 资源
     */
    void add(ResourceDTO dto);

    /**
     * 更新资源
     * @param id 主键
     * @param dto 资源
     */
    void update(String id, ResourceDTO dto);

    /**
     * 删除资源
     * @param id 主键
     */
    void remove(String id);

    /**
     * 递归查找所有的子集
     * @param resource 资源
     */
    void findAllChild(SysResource resource);

    /**
     * 获取资源所有的父级
     * @param resource 资源
     * @param cacheMap 缓存对象
     * @param cacheMap2 缓存对象
     * @return 资源
     */
    SysResource getResourceAllParent(SysResource resource, Map<String,SysResource> cacheMap,
                                     Map<String,SysResource> cacheMap2);

    /**
     * 查询菜单树,用户ID和用户名为空则查询全部
     * @param resultType 菜单结果类型，0：包含按钮，1：不包含按钮
     * @param userName 用户名
     * @return 资源列表
     */
    List<SysResource> findTree(String userName, ResourceConstants.ResultType resultType);

    /**
     * 根据用户名获取该用户授权的资源（超级管理员具有所有资源权限）
     * @param username 用户名
     * @return 资源列表
     */
    List<SysResource> findByUser(String username);

    /**
     * 获取资源列表
     * @param searchText 过滤文本
     * @return 资源树
     */
    List<SysResource> treeList(String searchText);
}
