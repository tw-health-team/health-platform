package com.theus.health.base.service.system.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theus.health.base.common.constants.SysConstants;
import com.theus.health.base.mapper.system.SysDeptCenterMapper;
import com.theus.health.base.model.dto.system.deptCenter.DeptCenterAddDTO;
import com.theus.health.base.model.dto.system.deptCenter.DeptCenterUpdateDTO;
import com.theus.health.base.model.dto.system.deptCenter.FindDeptCenterDTO;
import com.theus.health.base.model.po.system.SysDeptCenter;
import com.theus.health.base.service.system.SysDeptCenterService;
import com.theus.health.base.util.ShiroUtils;
import com.theus.health.core.exception.BusinessException;
import com.theus.health.core.util.ChinesePinyinUtil;
import com.theus.health.core.util.WuBiUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author libin
 * @date 2019-10-24 20:53
 */
@Service
public class SysDeptCenterServiceImpl extends ServiceImpl<SysDeptCenterMapper, SysDeptCenter> implements SysDeptCenterService{

    @Resource
    private  SysDeptCenterMapper sysDeptCenterMapper;

    @Override
    public SysDeptCenter findById(String id) {
        return this.getOne(new QueryWrapper<SysDeptCenter>().eq("id", id));
    }

    @Override
    public SysDeptCenter findByName(String name) {
        return this.getOne(new QueryWrapper<SysDeptCenter>().eq("name", name));
    }

    @Override
    public void add(DeptCenterAddDTO addDTO) {
        SysDeptCenter findDept = this.findById(addDTO.getId());
        if (findDept != null) {
            throw BusinessException.fail(String.format("已经存在中心科室ID为 %s 的科室", addDTO.getId()));
        }
        findDept = this.findByName(addDTO.getName());
        if (findDept != null) {
            throw BusinessException.fail(String.format("已经存在中心科室名称为 %s 的科室", addDTO.getName()));
        }
        findDept = new SysDeptCenter();
        BeanUtils.copyProperties(addDTO, findDept);
        // 上级中心科室为空 则赋默认值0
        if(StrUtil.isBlank(findDept.getParentId())) {
            findDept.setParentId(SysConstants.TOP_DEPT_CODE);
        }
        // 获取中心科室名称拼音码
        findDept.setSimpleSpelling(ChinesePinyinUtil.getPinYinHeadChar(findDept.getName()));
        // 获取名称的五笔码
        findDept.setSimpleWubi(WuBiUtil.getWubiHeadChar(findDept.getName()));
        findDept.setCreateTime(new Date());

        // 获取登录用户名--待优化
        findDept.setCreateBy(ShiroUtils.getUser().getName());

        this.save(findDept);
    }

    @Override
    public void update(DeptCenterUpdateDTO updateDTO) {
        SysDeptCenter deptCenter = this.getById(updateDTO.getId());
        if (deptCenter == null) {
            throw BusinessException.fail(
                    String.format("更新失败，不存在ID为 %s 的中心科室", updateDTO.getId()));
        }
        SysDeptCenter findDept = this.getOne(new QueryWrapper<SysDeptCenter>()
                .eq("name", updateDTO.getName()).ne("id", updateDTO.getId()));
        if (findDept != null) {
            throw BusinessException.fail(
                    String.format("更新失败，已经存在中心科室名为 %s 的中心科室", updateDTO.getName()));
        }
        // 名称改变 重新生成拼音码
        if(!deptCenter.getName().equals(updateDTO.getName())){
            // 获取机构名称的拼音首字母
            deptCenter.setSimpleSpelling(ChinesePinyinUtil.getPinYinHeadChar(updateDTO.getName()));
            deptCenter.setSimpleWubi(WuBiUtil.getWubiHeadChar(updateDTO.getName()));
        }
        BeanUtils.copyProperties(updateDTO, deptCenter);
        try {
            this.updateById(deptCenter);
        } catch (BusinessException e) {
            throw BusinessException.fail(e.getMsg(), e);
        } catch (Exception e) {
            throw BusinessException.fail("中心科室信息更新失败", e);
        }
    }

    @Override
    public List<SysDeptCenter> findTree(FindDeptCenterDTO findDeptCenterDTO) {
        List<SysDeptCenter> sysDeptCenters;
        sysDeptCenters = sysDeptCenterMapper.findTree(findDeptCenterDTO);
        for (SysDeptCenter deptCenter : sysDeptCenters) {
            // 保存顶级机构
            if (StrUtil.isBlank(deptCenter.getParentId())) {
                deptCenter.setLevel(0);
            }
        }
        //循环生成子节点
        findChildren(sysDeptCenters,new ArrayList<>(sysDeptCenters));
        // 去除重复的字典
        sysDeptCenters = removeDuplicate(sysDeptCenters);
        return sysDeptCenters;
    }

    /**
     * 根中心科室列表生成中心科室树
     * @param DeptCenterList 中心科室列表
     * @return 中心科室树
     */
    private List<SysDeptCenter> initTree(List<SysDeptCenter> DeptCenterList){
        List<SysDeptCenter> sysDeptCenters = new ArrayList<>();
        if (DeptCenterList != null) {
            // 循环字典列表
            for (SysDeptCenter DeptCenter : DeptCenterList) {
                // 保存顶级字典
                if (DeptCenter.getParentId() == null || "0".equals(DeptCenter.getParentId())) {
                    DeptCenter.setLevel(0);
                    sysDeptCenters.add(DeptCenter);
                }
            }
            // 根据顶级字典和字典列表生成字典树
            findChildren(sysDeptCenters, DeptCenterList);
        }
        return sysDeptCenters;
    }

    /**
     * 根据上级中心科室和中心科室列表生成中心科室树
     * @param sysDeptCenters 上级中心科室list
     * @param DeptCenters 中心科室列表
     */
    private void findChildren(List<SysDeptCenter> sysDeptCenters, List<SysDeptCenter> DeptCenters) {
        // 循环上级中心科室
        for (SysDeptCenter sysDeptCenter : sysDeptCenters) {
            List<SysDeptCenter> children = new ArrayList<>();
            // 循环中心科室列表
            for (SysDeptCenter DeptCenter : DeptCenters) {
                // 将中心科室列表中的父id是上级中心科室的id的中心科室存入子中心科室列表
                if (sysDeptCenter.getId() != null && sysDeptCenter.getId().equals(DeptCenter.getParentId())) {
                    DeptCenter.setParentName(sysDeptCenter.getName());
                    DeptCenter.setLevel(sysDeptCenter.getLevel() + 1);
                    children.add(DeptCenter);
                }
            }
            sysDeptCenter.setChildren(children);
            findChildren(children, DeptCenters);
        }
    }

    /**
     * 获取中心科室的下级中心科室信息
     *
     * @param sysDeptCenter 中心科室信息
     */
    private void findChildren(SysDeptCenter sysDeptCenter){
        List<SysDeptCenter> sysDeptCenters = this.list(new QueryWrapper<SysDeptCenter>().eq("parent_id", sysDeptCenter.getId()));
        sysDeptCenters.forEach(v-> v.setParentName(sysDeptCenter.getName()));
        sysDeptCenter.setChildren(sysDeptCenters);
        // 循环中心科室列表
        for (SysDeptCenter DeptCenter : sysDeptCenters) {
            findChildren(DeptCenter);
        }
    }

    private List<SysDeptCenter> removeDuplicate(List<SysDeptCenter> DeptCenterList){
        List<SysDeptCenter> sysDeptCenters = new ArrayList<>();
        // 循环中心科室
        for (SysDeptCenter DeptCenter : DeptCenterList) {
            boolean isExists = false;
            // 循环其他中心科室，判断是否存在于其他中心科室的子中心科室中
            for (SysDeptCenter deptCenter : DeptCenterList) {
                if(!DeptCenter.getId().equals(deptCenter.getId())){
                    if(isExistsInList(DeptCenter,deptCenter.getChildren())){
                        isExists = true;
                        break;
                    }
                }
            }
            // 若不存在与其他中心科室的子中心科室中则保留
            if(!isExists){
                sysDeptCenters.add(DeptCenter);
            }
        }
        return sysDeptCenters;
    }

    private boolean isExistsInList(SysDeptCenter sysDeptCenter,List<SysDeptCenter> DeptCenterList) {
        boolean isExists = false;
        if (DeptCenterList != null) {
            for (SysDeptCenter DeptCenter : DeptCenterList) {
                if (sysDeptCenter.getId().equals(DeptCenter.getId())) {
                    isExists = true;
                    break;
                }
                if (!isExists) {
                    isExists = isExistsInList(sysDeptCenter, DeptCenter.getChildren());
                }
            }
        }
        return isExists;
    }
}
