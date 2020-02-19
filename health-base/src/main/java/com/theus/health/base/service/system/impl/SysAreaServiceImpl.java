package com.theus.health.base.service.system.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theus.health.base.common.constants.SysConstants;
import com.theus.health.base.mapper.system.SysAreaMapper;
import com.theus.health.base.model.dto.system.area.AreaAddDTO;
import com.theus.health.base.model.dto.system.area.AreaUpdateDTO;
import com.theus.health.base.model.dto.system.area.FindAreaDTO;
import com.theus.health.base.model.po.system.SysArea;
import com.theus.health.base.service.system.SysAreaService;
import com.theus.health.base.util.ShiroUtils;
import com.theus.health.core.exception.BusinessException;
import com.theus.health.core.util.ChinesePinyinUtil;
import com.theus.health.core.util.WuBiUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author libin
 * @date 2019-12-12 15:00
 */
@Service
public class SysAreaServiceImpl extends ServiceImpl<SysAreaMapper, SysArea> implements SysAreaService {

    @Resource
    private SysAreaMapper sysAreaMapper;
    /**
     * 根据地址编码查询地址信息
     * @param id 地址编码
     * @return
     */
    @Override
    public SysArea findById(String id) {
        return this.getOne(new QueryWrapper<SysArea>().eq("id", id));
    }

    /**
     * 获取地址信息树形信息
     * @param findAreaDTO 查询条件
     * @return
     */
    @Override
    public List<SysArea> findTree(FindAreaDTO findAreaDTO) {
        List<SysArea> sysAreas;
        sysAreas = sysAreaMapper.findTree(findAreaDTO);
        for (SysArea area : sysAreas) {
            // 保存顶级地址
            if (StrUtil.isBlank(area.getParentId()) || "0".equals(area.getParentId())) {
                area.setLevel(0);
            }
        }
        //循环生成子节点
        findChildren(sysAreas,new ArrayList<>(sysAreas));
        // 去除重复的字典
        sysAreas = removeDuplicate(sysAreas);
        return sysAreas;
    }

    /**
     * 根地址列表生成地址树
     * @param areaList 地址列表
     * @return 地址树
     */
    private List<SysArea> initTree(List<SysArea> areaList){
        List<SysArea> sysAreas = new ArrayList<>();
        if (areaList != null) {
            // 循环字典列表
            for (SysArea  area : areaList) {
                // 保存顶级字典
                if (area.getParentId() == null || "0".equals(area.getParentId())) {
                    area.setLevel(0);
                    sysAreas.add(area);
                }
            }
            // 根据顶级字典和字典列表生成字典树
            findChildren(sysAreas, areaList);
        }
        return sysAreas;
    }

    /**
     * 根据上级地址和地址列表生成地址树
     * @param sysAreas 上级地址list
     * @param areas 地址列表
     */
    private void findChildren(List<SysArea> sysAreas, List<SysArea> areas) {
        // 循环上级地址
        for (SysArea sysArea : sysAreas) {
            List<SysArea> children = new ArrayList<>();
            // 循环地址列表
            for (SysArea area : areas) {
                // 将地址列表中的父id是上级地址的id的地址存入子地址列表
                if (sysArea.getId() != null && sysArea.getId().equals(area.getParentId())) {
                    area.setParentName(sysArea.getName());
                    area.setLevel(sysArea.getLevel() + 1);
                    children.add(area);
                }
            }
            sysArea.setChildren(children);
            findChildren(children, areas);
        }
    }

    /**
     * 获取地址的下级中心科室信息
     *
     * @param sysArea 地址信息
     */
    private void findChildren(SysArea sysArea){
        List<SysArea> sysAreas = this.list(new QueryWrapper<SysArea>().eq("parent_id", sysArea.getId()));
        sysAreas.forEach(v-> v.setParentName(sysArea.getName()));
        sysArea.setChildren(sysAreas);
        // 循环地址列表
        for (SysArea area : sysAreas) {
            findChildren(area);
        }
    }

    private List<SysArea> removeDuplicate(List<SysArea> areaList){
        List<SysArea> sysAreas = new ArrayList<>();
        // 循环地址
        for (SysArea sysArea : areaList) {
            boolean isExists = false;
            // 循环其他地址，判断是否存在于其他地址的子地址中
            for (SysArea area : areaList) {
                if(!sysArea.getId().equals(area.getId())){
                    if(isExistsInList(sysArea,area.getChildren())){
                        isExists = true;
                        break;
                    }
                }
            }
            // 若不存在与其他地址的子地址中则保留
            if(!isExists){
                sysAreas.add(sysArea);
            }
        }
        return sysAreas;
    }

    private boolean isExistsInList(SysArea sysArea,List<SysArea> areaList) {
        boolean isExists = false;
        if (areaList != null) {
            for (SysArea area : areaList) {
                if (sysArea.getId().equals(area.getId())) {
                    isExists = true;
                    break;
                }
                if (!isExists) {
                    isExists = isExistsInList(sysArea, area.getChildren());
                }
            }
        }
        return isExists;
    }

    /**
     * 新增地址信息
     * @param addDTO 新增数据集
     */
    @Override
    public void add(AreaAddDTO addDTO) {
        SysArea findArea = this.findById(addDTO.getId());
        if (findArea != null) {
            throw BusinessException.fail(String.format("已经存在地址编码ID为 %s 的地址信息", addDTO.getId()));
        }
        findArea = new SysArea();
        BeanUtils.copyProperties(addDTO, findArea);
        // 上级地址为空 则赋默认值0
        if(StrUtil.isBlank(findArea.getParentId())) {
            findArea.setParentId(SysConstants.TOP_COMMON_CODE);
        }
        // 获取地址全称拼音码
        findArea.setSimpleSpelling(ChinesePinyinUtil.getPinYinHeadChar(findArea.getFullName()));
        // 获取地址全称五笔码
        findArea.setSimpleWubi(WuBiUtil.getWubiHeadChar(findArea.getFullName()));

        this.save(findArea);
    }

    /**
     * 修改地址信息
     * @param updateDTO 修改数据集
     */
    @Override
    public void update(AreaUpdateDTO updateDTO) {
        SysArea area = this.getById(updateDTO.getId());
        if (area == null) {
            throw BusinessException.fail(
                    String.format("更新失败，不存在ID为 %s 的地址信息", updateDTO.getId()));
        }
        // 名称改变 重新生成拼音码
        if(!area.getFullName().equals(updateDTO.getFullName())){
            // 获取地址全称拼音码
            area.setSimpleSpelling(ChinesePinyinUtil.getPinYinHeadChar(updateDTO.getFullName()));
            // 获取地址全称五笔码
            area.setSimpleWubi(WuBiUtil.getWubiHeadChar(updateDTO.getFullName()));
        }
        BeanUtils.copyProperties(updateDTO, area);
        try {
            this.updateById(area);
        } catch (BusinessException e) {
            throw BusinessException.fail(e.getMsg(), e);
        } catch (Exception e) {
            throw BusinessException.fail("地址信息更新失败", e);
        }
    }
}
