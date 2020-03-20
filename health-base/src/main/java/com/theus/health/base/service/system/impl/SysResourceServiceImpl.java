package com.theus.health.base.service.system.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theus.health.base.common.constants.ResourceConstants;
import com.theus.health.base.common.constants.SysConstants;
import com.theus.health.base.mapper.system.SysResourceMapper;
import com.theus.health.base.model.dto.system.resource.ResourceDTO;
import com.theus.health.base.model.po.system.SysResource;
import com.theus.health.base.service.global.ShiroService;
import com.theus.health.base.service.system.SysResourceService;
import com.theus.health.core.exception.BusinessException;
import com.theus.health.core.util.ChinesePinyinUtil;
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
    private SysResourceMapper sysResourceMapper;

    @Override
    public List<SysResource> treeList() {
        return this.listResourceToTree(this.findAllResource());
    }

    @Override
    public void add(ResourceDTO dto) {
        if (ResourceConstants.Type.MENU.ordinal() == dto.getType()) {
            if (StrUtil.isBlank(dto.getUrl())) {
                throw BusinessException.fail("菜单路由不能为空！");
            }
        }
        SysResource resource = new SysResource();
        BeanUtils.copyProperties(dto, resource);
        resource.setCreateDate(new Date());
        // 获取拼音首字母
        resource.setSimpleSpelling(ChinesePinyinUtil.getPinYinHeadChar(dto.getName()));
        this.save(resource);
        shiroService.reloadPerms();
    }

    @Override
    public void update(String id, ResourceDTO dto) {
        if (ResourceConstants.Type.MENU.ordinal() == dto.getType()) {
            if (StrUtil.isBlank(dto.getUrl())) {
                throw BusinessException.fail("菜单路由不能为空！");
            }
        }
        SysResource resource = this.getById(id);
        if (resource == null) {
            throw BusinessException.fail("更新失败，不存在ID为" + id + "的资源");
        }
        // 名称改变 重新生成拼音码
        if (!resource.getName().equals(dto.getName())) {
            // 获取拼音首字母
            resource.setSimpleSpelling(ChinesePinyinUtil.getPinYinHeadChar(dto.getName()));
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
                // 判断是否已经包含此对象
                if (!parent.getChildren().contains(resource)) {
                    parent.getChildren().add(resource);
                }
                cacheMap.put(resource.getParentId(), parent);
                // 如果此父级还有父级，继续递归查询
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
    public List<SysResource> findTree(String userName, ResourceConstants.ResultType resultType) {
        // 获取授权的资源
        List<SysResource> resources = findByUser(userName);
        if (resultType == ResourceConstants.ResultType.NO_BUTTON) {
            // 删除按钮
            resources.removeIf(resource -> ResourceConstants.Type.BUTTON.ordinal() == resource.getType());
        }
        return this.listResourceToTree(resources);
    }

    @Override
    public List<SysResource> findByUser(String username) {
        List<SysResource> resources;
        // 用户名为空 或者是超级管理员 则获取所有资源
        if (StrUtil.isBlank(username) || SysConstants.SUPER_ADMIN.equalsIgnoreCase(username)) {
            // 获取资源
            resources = this.findAllResource();
        } else {
            // 查询用户授权的资源
            resources = sysResourceMapper.findByUserName(username);
        }
        return resources;
    }

    /**
     * 获取所有资源
     *
     * @return 资源列表
     */
    @Override
    public List<SysResource> findAllResource() {
        QueryWrapper<SysResource> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        // 获取资源
        return this.list(wrapper);
    }

    @Override
    public List<SysResource> treeList(String searchText) {
        if (StrUtil.isBlank(searchText)) {
            return this.treeList();
        } else {
            List<SysResource> allResource = this.findAllResource();
            return listToTreeByKeywords(allResource, searchText);
        }
    }

    /**
     * 说明方法描述：使list转换为树并根据关键字和节点名称过滤
     *
     * @param allResources 所有节点
     * @param keywords     要过滤的关键字
     * @return 树结构
     */
    public List<SysResource> listToTreeByKeywords(List<SysResource> allResources, String keywords) {
        List<SysResource> resultList = new ArrayList<>();
        Map<String, SysResource> allMapOfId = new HashMap<>(100);
        // 1.获取模糊查询的数据（从源数据集删除，剩余的保存到map中）
        if (allResources.size() > 0) {
            Iterator<SysResource> iterator = allResources.iterator();
            while (iterator.hasNext()) {
                SysResource resource = iterator.next();
                // 名称包含过滤关键字,则保存
                if (resource.getName().contains(keywords)) {
                    resultList.add(resource);
                    // 从列表删除
                    iterator.remove();
                } else {
                    // 其他的保存到map中
                    allMapOfId.put(resource.getId(), resource);
                }
            }
        }
        // 2.获取子集
        List<SysResource> childList = this.getChildList(resultList, new ArrayList<>(), new HashMap<>(20), allResources);
        // 3.获取自身及父集
        resultList = this.getSelfAndTheirParentList(resultList, new ArrayList<>(), new HashMap<>(20), allMapOfId);
        // 4.合并
        resultList.addAll(childList);
        // 5.将list集转为树tree结构
        resultList = this.listResourceToTree(resultList);
        return resultList;
    }

    /**
     * 获取子集list
     *
     * @param parentList   父集
     * @param resultList   结果集
     * @param filteredMap  用于过滤子集的map
     * @param allResources 所有剩余资源
     * @return 子集
     */
    private List<SysResource> getChildList(List<SysResource> parentList, List<SysResource> resultList,
                                           Map<String, SysResource> filteredMap, List<SysResource> allResources) {
        // 获取的子集
        List<SysResource> childList = new ArrayList<>();
        if (allResources != null && allResources.size() > 0
                && parentList != null && parentList.size() > 0) {
            for (SysResource parent : parentList) {
                String parentId = parent.getId();
                Iterator<SysResource> iterator = allResources.iterator();
                while (iterator.hasNext()) {
                    SysResource resource = iterator.next();
                    // 获取子资源
                    if (parentId.equals(resource.getParentId()) && !filteredMap.containsKey(parentId)) {
                        // 存储获取到的子集
                        filteredMap.put(resource.getId(), resource);
                        // 保存结果集
                        resultList.add(resource);
                        // 保存此次循环得到的子集 用于下次循环
                        childList.add(resource);
                        iterator.remove();
                    }
                }
            }
            this.getChildList(childList, resultList, filteredMap, allResources);
        }
        return resultList;
    }

    /**
     * 说明方法描述：递归找出本节点和他们的父节点
     *
     * @param parentList      根据关键字过滤出来的相关节点的父节点
     * @param resultList      返回的过滤出来的节点
     * @param filterRecordMap 已经过滤出来的节点
     * @param allRecordMap    所有节点
     * @return selfAndParentList
     */
    private List<SysResource> getSelfAndTheirParentList(List<SysResource> parentList, List<SysResource> resultList,
                                                        Map<String, SysResource> filterRecordMap,
                                                        Map<String, SysResource> allRecordMap) {
        // 当父节点为null或者节点数量为0时返回结果，退出递归
        if (parentList == null || parentList.size() == 0) {
            return resultList;
        }
        // 重新创建父节点集合
        List<SysResource> listParent = new ArrayList<>();
        // 遍历已经过滤出来的节点
        for (SysResource record : parentList) {
            String id = record.getId();
            String parentId = record.getParentId();
            // 如果已经过滤出来的节点不存在则添加到list中
            if (!filterRecordMap.containsKey(id)) {
                // 添加到父节点中
                listParent.add(record);
                // 添加到已过滤的map中
                filterRecordMap.put(id, record);
                // 移除集合中相应的元素
                allRecordMap.remove(id);
                // 添加到结果集中
                resultList.add(record);
            }
            // 找出本节点的父节点并添加到listParentRecord父节点集合中，并移除集合中相应的元素
            if (StrUtil.isNotBlank(parentId)) {
                SysResource parentRecord = allRecordMap.get(parentId);
                if (parentRecord != null) {
                    listParent.add(parentRecord);
                    allRecordMap.remove(parentId);
                }
            }
        }
        // 递归调用
        getSelfAndTheirParentList(listParent, resultList, filterRecordMap, allRecordMap);
        return resultList;
    }

    /**
     * 说明方法描述：将list转为树tree结构
     *
     * @param allResources 所有资源
     * @return 树结构
     */
    private List<SysResource> listResourceToTree(List<SysResource> allResources) {
        List<SysResource> listParent = new ArrayList<>();
        if (allResources == null || allResources.size() == 0) {
            return listParent;
        }
        List<SysResource> listNotParent = new ArrayList<>();
        // 第一步：保存所有的id
        List<String> allID = new ArrayList<>();
        for (SysResource resource : allResources) {
            String id = resource.getId();
            allID.add(id);
        }
        // 第二步：遍历allRecords找出所有的根节点和非根节点
        for (SysResource resource : allResources) {
            String parentId = resource.getParentId();
            if (!allID.contains(parentId)) {
                listParent.add(resource);
            } else {
                listNotParent.add(resource);
            }
        }
        // 第三步： 递归获取所有子节点
        this.createTree(listParent, listNotParent);
        return listParent;
    }

    /**
     * 根据父节点和非父节点生成树
     *
     * @param listParent    父节点
     * @param listNotParent 非父节点
     */
    private void createTree(List<SysResource> listParent, List<SysResource> listNotParent) {
        if (listParent.size() > 0) {
            for (SysResource resource : listParent) {
                // 添加所有子级
                resource.setChildren(this.getChildTree(listNotParent, resource.getId()));
            }
        }
    }

    /**
     * 根据资源列表生成资源树
     *
     * @param childList 资源列表
     * @param parentId  父级id
     * @return 资源树
     */
    private List<SysResource> getChildTree(List<SysResource> childList, String parentId) {
        List<SysResource> parentList = new ArrayList<>();
        List<SysResource> notParentList = new ArrayList<>();
        // 遍历childList，找出所有的根节点和非根节点
        if (childList != null && childList.size() > 0) {
            for (SysResource resource : childList) {
                // 对比找出父节点
                if (resource.getParentId().equals(parentId)) {
                    parentList.add(resource);
                } else {
                    notParentList.add(resource);
                }
            }
        }
        // 递归获取所有子节点
        this.createTree(parentList, notParentList);
        return parentList;
    }
}
