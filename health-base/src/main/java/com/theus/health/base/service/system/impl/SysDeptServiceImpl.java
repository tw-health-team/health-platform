package com.theus.health.base.service.system.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theus.health.base.mapper.system.SysDeptMapper;
import com.theus.health.base.model.dto.system.dept.DeptAddDTO;
import com.theus.health.base.model.dto.system.dept.DeptUpdateDTO;
import com.theus.health.base.model.dto.system.dept.FindDeptDTO;
import com.theus.health.base.model.po.system.SysDept;
import com.theus.health.base.service.system.SysDeptService;
import com.theus.health.core.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tangwei
 * @date 2019-09-25 20:19
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Override
    public SysDept findDeptByName(String name) {
        return this.getOne(new QueryWrapper<SysDept>().eq("name", name));
    }

    @Override
    public void add(DeptAddDTO addDTO) {
        SysDept findDept = this.findDeptByName(addDTO.getName());
        if (findDept != null) {
            throw BusinessException.fail(String.format("已经存在机构名为 %s 的机构", addDTO.getName()));
        }
        try {
            findDept = new SysDept();
            BeanUtils.copyProperties(addDTO, findDept);
            findDept.setCreateTime(new Date());
            this.save(findDept);
        } catch (Exception e) {
            throw BusinessException.fail("添加机构失败", e);
        }
    }

    @Override
    public void update(String id, DeptUpdateDTO updateDTO) {
        SysDept dept = this.getById(id);
        if (dept == null) {
            throw BusinessException.fail(
                    String.format("更新失败，不存在ID为 %s 的机构", id));
        }
        SysDept findDept = this.getOne(new QueryWrapper<SysDept>()
                .eq("name", updateDTO.getName()).ne("id", id));
        if (findDept != null) {
            throw BusinessException.fail(
                    String.format("更新失败，已经存在机构名为 %s 的机构", updateDTO.getName()));
        }
        BeanUtils.copyProperties(updateDTO, dept);
        try {
            this.updateById(dept);
        } catch (BusinessException e) {
            throw BusinessException.fail(e.getMsg(), e);
        } catch (Exception e) {
            throw BusinessException.fail("机构信息更新失败", e);
        }
    }

    @Override
    public List<SysDept> findTree(FindDeptDTO findDeptDTO) {
        List<SysDept> sysDepts;
        String name = findDeptDTO.getName();
        List<SysDept> allDepts;
        if (StrUtil.isBlank(name)) {
            allDepts = this.list();
            sysDepts = initTree(allDepts);
        } else {
            sysDepts = this.list(new QueryWrapper<SysDept>().like("name", findDeptDTO.getName()));
            // 循环机构列表
            for (SysDept dept : sysDepts) {
                // 不是顶级机构 则获取上级机构的名称
                if (StrUtil.isNotBlank(dept.getParentId()) && !"0".equals(dept.getParentId())) {
                    SysDept sysDept = this.getOne(new QueryWrapper<SysDept>()
                            .eq("id", dept.getParentId()).select("name"));
                    dept.setParentName(sysDept.getName());
                }
                findChildren(dept);
            }
            // 去除重复的机构
            sysDepts = removeDuplicate(sysDepts);
        }
        return sysDepts;
    }

    /**
     * 根据机构列表生成机构树
     * @param deptList 机构列表
     * @return 机构树
     */
    private List<SysDept> initTree(List<SysDept> deptList){
        List<SysDept> sysDepts = new ArrayList<>();
        if (deptList != null) {
            // 循环机构列表
            for (SysDept dept : deptList) {
                // 保存顶级机构
                if (dept.getParentId() == null || "0".equals(dept.getParentId())) {
                    dept.setLevel(0);
                    sysDepts.add(dept);
                }
            }
            // 根据顶级机构和机构列表生成机构树
            findChildren(sysDepts, deptList);
        }
        return sysDepts;
    }

    /**
     * 根据上级机构和机构列表生成机构树
     * @param sysDepts 上级机构list
     * @param depts 机构列表
     */
    private void findChildren(List<SysDept> sysDepts, List<SysDept> depts) {
        // 循环上级机构
        for (SysDept sysDept : sysDepts) {
            List<SysDept> children = new ArrayList<>();
            // 循环机构列表
            for (SysDept dept : depts) {
                // 将机构列表中的父id是上级机构的id的机构存入子机构列表
                if (sysDept.getId() != null && sysDept.getId().equals(dept.getParentId())) {
                    dept.setParentName(sysDept.getName());
                    dept.setLevel(sysDept.getLevel() + 1);
                    children.add(dept);
                }
            }
            sysDept.setChildren(children);
            findChildren(children, depts);
        }
    }

    /**
     * 获取机构的下级机构信息
     *
     * @param sysDept 机构信息
     */
    private void findChildren(SysDept sysDept){
        List<SysDept> sysDepts = this.list(new QueryWrapper<SysDept>().eq("parent_id", sysDept.getId()));
        sysDepts.forEach(v-> v.setParentName(sysDept.getName()));
        sysDept.setChildren(sysDepts);
        // 循环机构列表
        for (SysDept dept : sysDepts) {
            findChildren(dept);
        }
    }

    private List<SysDept> removeDuplicate(List<SysDept> deptList){
        List<SysDept> sysDepts = new ArrayList<>();
        // 循环机构
        for (SysDept dept : deptList) {
            boolean isExists = false;
            // 循环其他机构，判断是否存在于其他机构的子机构中
            for (SysDept otherDept : deptList) {
                if(!dept.getId().equals(otherDept.getId())){
                    if(isExistsInList(dept,otherDept.getChildren())){
                        isExists = true;
                        break;
                    }
                }
            }
            // 若不存在与其他机构的子机构中则保留
            if(!isExists){
                sysDepts.add(dept);
            }
        }
        return sysDepts;
    }

    private boolean isExistsInList(SysDept sysDept,List<SysDept> deptList) {
        boolean isExists = false;
        if (deptList != null) {
            for (SysDept otherDept : deptList) {
                if (sysDept.getId().equals(otherDept.getId())) {
                    isExists = true;
                    break;
                }
                if (!isExists) {
                    isExists = isExistsInList(sysDept, otherDept.getChildren());
                }
            }
        }
        return isExists;
    }
}
