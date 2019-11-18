package com.theus.health.base.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.theus.health.base.model.dto.system.dict.DictExistsQueryDTO;
import com.theus.health.base.model.dto.system.dict.FindDictDTO;
import com.theus.health.base.model.po.system.SysDict;
import com.theus.health.base.model.po.system.SysDictClass;
import com.theus.health.base.model.vo.dict.DictClassExistsQueryDTO;
import com.theus.health.base.model.vo.dict.FindClassVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tangwei
 * @date 2019-10-07 22:18
 */
@Mapper
@Repository
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * 查询用户列表
     * @param findUserDTO 查询条件
     * @return 用户list
     */
    List<SysDict> findList(@Param("param") FindDictDTO findUserDTO);

    /**
     * 查询所有字典分类数据
     * @return 字典分类list
     */
    List<SysDictClass> findAllDictClasses();

    /**
     * 查询字典分类数据
     * @return 字典分类list
     */
    List<SysDictClass> findDictClassList(@Param("param") FindClassVO findClassVO);

    /**
     * 查询字典是否存在
     * @param existsQueryDTO 字典信息
     * @return 数量
     */
    int existsDict(@Param("dict") DictExistsQueryDTO existsQueryDTO);

    /**
     * 查询字典分类是否存在
     * @param queryDTO 条件
     * @return 数量
     */
    int existsDictClass(@Param("dictClass") DictClassExistsQueryDTO queryDTO);

    /**
     * 插入字典分类
     * @param sysDictClass 字典分类
     * @return 数量
     */
    int insertDictClass(SysDictClass sysDictClass);

    /**
     * 更新字典分类
     * @param sysDictClass 字典分类
     * @return 数量
     */
    int updateDictClass(SysDictClass sysDictClass);

    /**
     * 删除字典分类
     * @param id id
     * @return 数量
     */
    int delDictClass(@Param("id")String id);

    /**
     * 通过父ID获取字典分类
     * @param parentId 父id
     * @return 字典分类
     */
    SysDictClass getClassByParentId(@Param("parentId")String parentId);
}

