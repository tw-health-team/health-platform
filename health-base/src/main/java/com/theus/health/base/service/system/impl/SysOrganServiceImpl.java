package com.theus.health.base.service.system.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theus.health.base.common.constants.DictConstants;
import com.theus.health.base.common.constants.SysConstants;
import com.theus.health.base.mapper.system.SysOrganMapper;
import com.theus.health.base.model.dto.system.organ.FindOrganDTO;
import com.theus.health.base.model.dto.system.organ.OrganAddDTO;
import com.theus.health.base.model.dto.system.organ.OrganUpdateDTO;
import com.theus.health.base.model.po.system.SysOrgan;
import com.theus.health.base.service.system.SysOrganService;
import com.theus.health.base.util.ShiroUtils;
import com.theus.health.core.exception.BusinessException;
import com.theus.health.core.util.ChinesePinyinUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author tangwei
 * @date 2019-09-25 20:19
 */
@Service
public class SysOrganServiceImpl extends ServiceImpl<SysOrganMapper, SysOrgan> implements SysOrganService {

    /**
     * 未删除标志
     */
    private final int NOT_DELETED = DictConstants.delFlag.NORMAL.getValue();

    @Override
    public SysOrgan findById(String id) {
        return this.getOne(new QueryWrapper<SysOrgan>().eq("id", id).eq("del_flag", this.NOT_DELETED));
    }

    @Override
    public SysOrgan findByName(String name) {
        return this.getOne(new QueryWrapper<SysOrgan>().eq("name", name).eq("del_flag", this.NOT_DELETED));
    }

    @Override
    public List<SysOrgan> findChildren(String id) {
        return this.list(new QueryWrapper<SysOrgan>().eq("parent_id", id).eq("del_flag", this.NOT_DELETED));
    }

    @Override
    public void add(OrganAddDTO addDTO) {
        // 校验
        SysOrgan findOrgan = this.findById(addDTO.getId());
        if (findOrgan != null) {
            throw BusinessException.fail(String.format("已经存在机构ID为 %s 的机构", addDTO.getId()));
        }
        findOrgan = this.findByName(addDTO.getName());
        if (findOrgan != null) {
            throw BusinessException.fail(String.format("已经存在机构名为 %s 的机构", addDTO.getName()));
        }
        // 转换实体
        findOrgan = new SysOrgan();
        BeanUtils.copyProperties(addDTO, findOrgan);
        // 完善机构数据
        this.completeOrgan(findOrgan, SysConstants.OperationType.ADD);
        this.save(findOrgan);
    }

    @Override
    public void update(String id, OrganUpdateDTO updateDTO) {
        SysOrgan organ = this.getById(id);
        if (organ == null) {
            throw BusinessException.fail(
                    String.format("更新失败，不存在ID为 %s 的机构", id));
        }
        // 名称改变 重新生成拼音码
        if (!organ.getName().equals(updateDTO.getName())) {
            // 获取机构名称的拼音首字母
            organ.setSimpleSpelling(ChinesePinyinUtil.getPinYinHeadChar(updateDTO.getName()));
        }
        SysOrgan findOrgan = this.getOne(new QueryWrapper<SysOrgan>()
                .eq("name", updateDTO.getName()).ne("id", id).eq("del_flag", this.NOT_DELETED));
        if (findOrgan != null) {
            throw BusinessException.fail(
                    String.format("更新失败，已经存在机构名为 %s 的机构", updateDTO.getName()));
        }
        BeanUtils.copyProperties(updateDTO, organ);
        // 完善机构数据
        this.completeOrgan(organ, SysConstants.OperationType.UPDATE);
        try {
            this.updateById(organ);
        } catch (BusinessException e) {
            throw BusinessException.fail(e.getMsg(), e);
        } catch (Exception e) {
            throw BusinessException.fail("机构信息更新失败", e);
        }
    }

    /**
     * 完善机构信息
     *
     * @param sysOrgan 机构
     * @param type     操作类型
     */
    private void completeOrgan(SysOrgan sysOrgan, int type) {
        if (sysOrgan != null) {
            String locationCode = sysOrgan.getLocationProvinceCode();
            String locationName = sysOrgan.getLocationProvinceName();
            if (StrUtil.isNotBlank(sysOrgan.getLocationCommitteeCode())) {
                locationCode = sysOrgan.getLocationCommitteeCode();
                locationName = sysOrgan.getLocationCommitteeName();
            } else if (StrUtil.isNotBlank(sysOrgan.getLocationTownCode())) {
                locationCode = sysOrgan.getLocationTownCode();
                locationName = sysOrgan.getLocationTownName();
            } else if (StrUtil.isNotBlank(sysOrgan.getLocationDistrictCode())) {
                locationCode = sysOrgan.getLocationDistrictCode();
                locationName = sysOrgan.getLocationDistrictName();
            } else if (StrUtil.isNotBlank(sysOrgan.getLocationCityCode())) {
                locationCode = sysOrgan.getLocationCityCode();
                locationName = sysOrgan.getLocationCityName();
            }
            sysOrgan.setLocationCode(locationCode);
            sysOrgan.setLocationName(locationName);
            if (type == SysConstants.OperationType.ADD) {
                // 上级机构为空 则赋默认值
                if (StrUtil.isBlank(sysOrgan.getParentId())) {
                    sysOrgan.setParentId(SysConstants.TOP_DEPT_CODE);
                }
                // 获取机构名称的拼音首字母
                sysOrgan.setSimpleSpelling(ChinesePinyinUtil.getPinYinHeadChar(sysOrgan.getName()));
                // 赋值新增固定字段
                ShiroUtils.setInsert(sysOrgan);
            } else {
                // 赋值修改固定字段
                ShiroUtils.setUpdate(sysOrgan);
            }
        }
    }

    @Override
    public List<SysOrgan> getOrganAndAllSubNode(String organId) {
        List<SysOrgan> sysOrgans = new ArrayList<>();
        List<SysOrgan> children;
        SysOrgan sysOrgan = this.findById(organId);
        children = this.getAllSubNode(organId);
        sysOrgans.add(sysOrgan);
        sysOrgans.addAll(children);
        return sysOrgans;
    }

    /**
     * 获取所有下级机构
     *
     * @param organId 机构id
     * @return 所有下级机构list
     */
    private List<SysOrgan> getAllSubNode(String organId) {
        List<SysOrgan> children = this.findChildren(organId);
        List<SysOrgan> grandChildren = new ArrayList<>();
        children.forEach(v -> grandChildren.addAll(this.getAllSubNode(v.getId())));
        children.addAll(grandChildren);
        return children;
    }

    /**
     * 获取所有机构
     *
     * @return 机构列表
     */
    private List<SysOrgan> findAllOrgans() {
        return this.baseMapper.all();
    }

    @Override
    public List<SysOrgan> findTree(FindOrganDTO findOrganDTO) {
        List<SysOrgan> sysOrgans;
        String name = findOrganDTO.getName();
        // 1、获取所有机构
        List<SysOrgan> allOrgans = this.findAllOrgans();
        // 登录用户的机构id
        String loginOrganId = ShiroUtils.getUser().getOrganId();
        // 2、获取登录机构及下级机构列表,id为空 则获取所有
        sysOrgans = this.getOrganList(loginOrganId, allOrgans);
        // 3、过滤文本不为空 则过滤
        sysOrgans = this.listToTreeByKeywords(sysOrgans, name);
        return sysOrgans;
    }

    /**
     * 根据机构id从所有机构列表中获取自身及下级机构
     *
     * @param organId   机构id
     * @param allOrgans 所有机构
     * @return 自身及下级机构列表
     */
    private List<SysOrgan> getOrganList(String organId, List<SysOrgan> allOrgans) {
        List<SysOrgan> parentList = new ArrayList<>();
        if (StrUtil.isNotBlank(organId)) {
            // 获取登录机构信息
            SysOrgan organ = null;
            for (SysOrgan sysOrgan : allOrgans) {
                if (sysOrgan.getId().equals(organId)) {
                    organ = sysOrgan;
                }
            }
            allOrgans.remove(organ);
            parentList.add(organ);
            // 获取子集
            List<SysOrgan> childList = this.getChildList(parentList, new ArrayList<>(), new HashMap<>(20), allOrgans);
            parentList.addAll(childList);
        } else {
            parentList = allOrgans;
        }
        return parentList;
    }

    /**
     * 说明方法描述：使list转换为树并根据关键字和节点名称过滤
     *
     * @param organList 机构列表
     * @param keywords  要过滤的关键字
     * @return 树结构
     */
    private List<SysOrgan> listToTreeByKeywords(List<SysOrgan> organList, String keywords) {
        List<SysOrgan> resultList = new ArrayList<>();
        // 1.过滤并获取子集
        if (StrUtil.isNotBlank(keywords)) {
            if (organList.size() > 0) {
                Iterator<SysOrgan> iterator = organList.iterator();
                while (iterator.hasNext()) {
                    SysOrgan sysOrgan = iterator.next();
                    // 名称、简称、简拼包含过滤关键字,则保存
                    if (sysOrgan.getName().contains(keywords) || sysOrgan.getShortName().contains(keywords)
                            || sysOrgan.getSimpleSpelling().contains(keywords)) {
                        resultList.add(sysOrgan);
                        // 从列表删除
                        iterator.remove();
                    }
                }
            }
            // 获取子集
            List<SysOrgan> childList = this.getChildList(resultList, new ArrayList<>(), new HashMap<>(20), organList);
            resultList.addAll(childList);
        } else {
            resultList = organList;
        }
        // 3.将list集转为树tree结构
        resultList = this.listOrganToTree(resultList);
        return resultList;
    }

    /**
     * 获取子集list
     *
     * @param parentList  父集
     * @param resultList  结果集
     * @param filteredMap 用于过滤子集的map
     * @param allOrgans   所有机构
     * @return 子集
     */
    private List<SysOrgan> getChildList(List<SysOrgan> parentList, List<SysOrgan> resultList,
                                        Map<String, SysOrgan> filteredMap, List<SysOrgan> allOrgans) {
        // 获取的子集
        List<SysOrgan> childList = new ArrayList<>();
        if (allOrgans != null && allOrgans.size() > 0
                && parentList != null && parentList.size() > 0) {
            for (SysOrgan parent : parentList) {
                String parentId = parent.getId();
                Iterator<SysOrgan> iterator = allOrgans.iterator();
                while (iterator.hasNext()) {
                    SysOrgan resource = iterator.next();
                    // 获取子资源
                    if (!filteredMap.containsKey(parentId) && parentId.equals(resource.getParentId())) {
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
            this.getChildList(childList, resultList, filteredMap, allOrgans);
        }
        return resultList;
    }

    /**
     * 说明方法描述：将list转为树tree结构
     *
     * @param allOrgans 所有机构
     * @return 树结构
     */
    private List<SysOrgan> listOrganToTree(List<SysOrgan> allOrgans) {
        List<SysOrgan> listParent = new ArrayList<>();
        if (allOrgans != null && allOrgans.size() > 0) {
            List<SysOrgan> listNotParent = new ArrayList<>();
            // 第一步：保存所有的id
            List<String> allID = new ArrayList<>();
            for (SysOrgan sysOrgan : allOrgans) {
                String id = sysOrgan.getId();
                allID.add(id);
            }
            // 第二步：遍历allOrgans找出所有的根节点和非根节点
            for (SysOrgan sysOrgan : allOrgans) {
                String parentId = sysOrgan.getParentId();
                if (!allID.contains(parentId)) {
                    listParent.add(sysOrgan);
                } else {
                    listNotParent.add(sysOrgan);
                }
            }
            // 第三步： 递归获取所有子节点
            this.createTree(listParent, listNotParent);
        }
        return listParent;
    }

    /**
     * 根据父节点和非父节点生成树
     *
     * @param listParent    父节点
     * @param listNotParent 非父节点
     */
    private void createTree(List<SysOrgan> listParent, List<SysOrgan> listNotParent) {
        if (listParent.size() > 0) {
            for (SysOrgan organ : listParent) {
                // 添加所有子级
                organ.setChildren(this.getChildTree(listNotParent, organ.getId()));
            }
        }
    }

    /**
     * 根据机构列表生成机构树
     *
     * @param childList 机构列表
     * @param parentId  父级id
     * @return 资源树
     */
    private List<SysOrgan> getChildTree(List<SysOrgan> childList, String parentId) {
        List<SysOrgan> parentList = new ArrayList<>();
        List<SysOrgan> notParentList = new ArrayList<>();
        // 遍历childList，找出所有的根节点和非根节点
        if (childList != null && childList.size() > 0) {
            for (SysOrgan organ : childList) {
                // 对比找出父节点
                if (organ.getParentId().equals(parentId)) {
                    parentList.add(organ);
                } else {
                    notParentList.add(organ);
                }
            }
        }
        // 递归获取所有子节点
        this.createTree(parentList, notParentList);
        return parentList;
    }
}
