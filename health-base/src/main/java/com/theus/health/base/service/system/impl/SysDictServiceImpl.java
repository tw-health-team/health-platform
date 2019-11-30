package com.theus.health.base.service.system.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theus.health.base.common.constants.SysConstants;
import com.theus.health.base.mapper.system.SysDictMapper;
import com.theus.health.base.model.dto.system.dict.*;
import com.theus.health.base.model.po.system.SysDict;
import com.theus.health.base.model.po.system.SysDictClass;
import com.theus.health.base.model.vo.dict.*;
import com.theus.health.base.service.system.SysDictService;
import com.theus.health.core.exception.BusinessException;
import com.theus.health.core.util.BaseConverter;
import com.theus.health.core.util.ChinesePinyinUtil;
import com.theus.health.core.util.WuBiUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author tangwei
 * @date 2019-10-07 22:16
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService{
    @Override
    public List<SysDict> findList(FindDictDTO findDictDTO) {
        return this.baseMapper.findList(findDictDTO);
    }

    @Override
    public List<SysDictDTO> findByType(String type) {
        List<SysDict> sysDictList = this.baseMapper.selectList(
                new QueryWrapper<SysDict>()
                        .eq("class_code", type)
                        .select("class_code","item_value","item_name")
                        .orderByAsc("sort"));
        List<SysDictDTO> sysDictDTOS = new ArrayList<>();
        sysDictList.forEach(v->{
            SysDictDTO sysDictDTO = new SysDictDTO();
            BeanUtils.copyProperties(v, sysDictDTO);
            sysDictDTOS.add(sysDictDTO);
        });
        return sysDictDTOS;
    }

    @Override
    public List<DictClassVO> findDictClassTree() {
        List<SysDictClass> sysDictClass = this.baseMapper.findAllDictClasses();
        return initTree(sysDictClass);
    }

    @Override
    public void addDictItem(DictAddDTO dictAddDTO) {
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(dictAddDTO, sysDict);
        // 判断字典项是否存在及完善字典项数据
        handleDictItem(sysDict);
        this.baseMapper.insert(sysDict);
    }

    @Override
    public void updateDictItem(DictUpdateDTO dictUpdateDTO) {
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(dictUpdateDTO, sysDict);
        // 判断字典项是否存在及完善字典项数据
        handleDictItem(sysDict);
        this.baseMapper.updateById(sysDict);
    }

    /**
     * 判断字典项是否存在及完善字典项数据
     * @param sysDict 字典项
     */
    private void handleDictItem(SysDict sysDict){
        // 判断字典项是否存在
        this.exists(sysDict);
        // 获取名称的拼音首字母
        sysDict.setSimpleSpelling(ChinesePinyinUtil.getPinYinHeadChar(sysDict.getItemName()));
        // 获取名称的五笔码
        sysDict.setSimpleWubi(WuBiUtil.getWubiHeadChar(sysDict.getItemName()));
    }

    @Override
    public boolean existsDict(DictExistsQueryDTO existsQueryDTO) {
        int num = this.baseMapper.existsDict(existsQueryDTO);
        return num > 0;
    }

    /**
     * 判断字典项是否存在
     * @param sysDict 字典
     */
    private void exists(SysDict sysDict){
        DictExistsQueryDTO dictExistsQueryDTO = new DictExistsQueryDTO();
        BeanUtils.copyProperties(sysDict,dictExistsQueryDTO);
        boolean bExists = existsDict(dictExistsQueryDTO);
        if (bExists) {
            throw BusinessException.fail("该字典项已存在！");
        }
    }

    @Override
    public List<DictClassVO> findDictClassList(FindClassVO findClassVO) {
        List<DictClassVO> dictClassVOList;
        List<SysDictClass> sysDictClassList;
        // 查询条件为空
        if (StrUtil.isBlank(findClassVO.getSearchText())) {
            sysDictClassList = this.baseMapper.findAllDictClasses();
            dictClassVOList = initTree(sysDictClassList);
        }else{
            sysDictClassList = this.baseMapper.findDictClassList(findClassVO);
            dictClassVOList = new BaseConverter<SysDictClass,DictClassVO>().convert(sysDictClassList,DictClassVO.class);
            findChildren(dictClassVOList, sysDictClassList);
            // 去除数据
            dictClassVOList = removeDuplicate(dictClassVOList);
        }
        return dictClassVOList;
    }

    /**
     * 根据字典分类列表生成字典分类树
     * @param dictClassList 字典分类列表
     * @return 字典分类树
     */
    private List<DictClassVO> initTree(List<SysDictClass> dictClassList){
        List<DictClassVO> list = new ArrayList<>();
        if (dictClassList != null) {
            // 循环字典分类列表
            for (SysDictClass sysDictClass : dictClassList) {
                // 保存顶级分类
                if (SysConstants.TOP_COMMON_CODE.equals(sysDictClass.getParentId())) {
                    DictClassVO dictClassVO = new DictClassVO();
                    BeanUtils.copyProperties(sysDictClass,dictClassVO);
                    list.add(dictClassVO);
                }
            }
            // 查找子分类
            findChildren(list, dictClassList);
        }
        return list;
    }

    /**
     * 根据上级字典分类和字典分类列表生成字典分类树
     * @param dictClassVOS 上级字典分类list
     * @param dictClassList 字典分类列表
     */
    private void findChildren(List<DictClassVO> dictClassVOS, List<SysDictClass> dictClassList) {
        // 循环上级
        for (DictClassVO dictClassVO : dictClassVOS) {
            List<DictClassVO> children = new ArrayList<>();
            // 循环列表
            for (SysDictClass sysDictClass : dictClassList) {
                // 将分类列表中的父id是上级分类的id的分类存入子分类列表
                if (dictClassVO.getId().equals(sysDictClass.getParentId())) {
                    DictClassVO tempVO = new DictClassVO();
                    BeanUtils.copyProperties(sysDictClass,tempVO);
                    tempVO.setParentName(dictClassVO.getName());
                    children.add(tempVO);
                }
            }
            dictClassVO.setChildren(children);
            findChildren(children, dictClassList);
        }
    }

    private List<DictClassVO> removeDuplicate(List<DictClassVO> dictClassVOList){
        List<DictClassVO> list = new ArrayList<>();
        // 循环
        for (DictClassVO dictClass : dictClassVOList) {
            boolean isExists = false;
            // 判断是否存在于其他字典分类的子集中
            for (DictClassVO other : dictClassVOList) {
                if(!dictClass.getId().equals(other.getId())){
                    if(isExistsInList(dictClass,other.getChildren())){
                        isExists = true;
                        break;
                    }
                }
            }
            // 若不存在与其他分类的子集中则保留
            if(!isExists){
                list.add(dictClass);
            }
        }
        return list;
    }

    /**
     * 是否存在于列表
     * @param dictClassVO 实体
     * @param dictClassList 实体list
     * @return 是否
     */
    private boolean isExistsInList(DictClassVO dictClassVO, List<DictClassVO> dictClassList) {
        boolean isExists = false;
        if (dictClassList != null) {
            for (DictClassVO other : dictClassList) {
                if (dictClassVO.getId().equals(other.getId())) {
                    isExists = true;
                    break;
                }
                if (!isExists) {
                    isExists = isExistsInList(dictClassVO, other.getChildren());
                }
            }
        }
        return isExists;
    }

    @Override
    public void addDictClass(DictClassAddVO dictClassAddVO) {
        SysDictClass sysDictClass = new SysDictClass();
        BeanUtils.copyProperties(dictClassAddVO,sysDictClass);
        // 生成主键待修改
        sysDictClass.setId(UUID.randomUUID().toString());
        // 处理字典项分类
        handleDictClass(sysDictClass);
        this.baseMapper.insertDictClass(sysDictClass);
    }

    @Override
    public void updateDictClass(DictClassUpdateVO dictClassUpdateVO) {
        SysDictClass sysDictClass = new SysDictClass();
        BeanUtils.copyProperties(dictClassUpdateVO,sysDictClass);
        // 处理字典项分类
        handleDictClass(sysDictClass);
        this.baseMapper.updateDictClass(sysDictClass);
    }

    /**
     * 判断字典项分类是否存在及完善字典项分类
     * @param sysDictClass 字典项分类
     */
    private void handleDictClass(SysDictClass sysDictClass){
        this.existsClass(sysDictClass);
        // 获取名称的拼音首字母
        sysDictClass.setSimpleSpelling(ChinesePinyinUtil.getPinYinHeadChar(sysDictClass.getName()));
        // 获取名称的五笔码
        sysDictClass.setSimpleWubi(WuBiUtil.getWubiHeadChar(sysDictClass.getName()));
    }

    @Override
    public boolean existsDictClass(DictClassExistsQueryDTO queryDTO) {
        int num = this.baseMapper.existsDictClass(queryDTO);
        return num > 0;
    }

    /**
     * 判断字典分类是否存在
     * @param sysDictClass 字典分类
     */
    private void existsClass(SysDictClass sysDictClass){
        DictClassExistsQueryDTO queryDTO = new DictClassExistsQueryDTO();
        BeanUtils.copyProperties(sysDictClass,queryDTO);
        boolean bExists = existsDictClass(queryDTO);
        if (bExists) {
            throw BusinessException.fail("该字典分类已存在！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeClass(String id) {
        // 删除所有子分类
        this.removeChildClass(id);
        this.baseMapper.delDictClass(id);
    }

    private void removeChildClass(String id) {
        SysDictClass sysDictClass = this.baseMapper.getClassByParentId(id);
        if (sysDictClass != null) {
            this.baseMapper.delDictClass(sysDictClass.getId());
            this.removeChildClass(sysDictClass.getId());
        }
    }
}
