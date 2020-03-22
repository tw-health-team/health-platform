package com.theus.health.base.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theus.health.base.model.dto.system.dict.*;
import com.theus.health.base.model.po.system.SysDict;
import com.theus.health.base.model.vo.dict.*;

import java.util.List;

/**
 * @author tangwei
 * @date 2019-10-07 22:05
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 查询字典数据
     * @param findDictDTO 过滤条件
     * @return RequestResult
     */
    List<SysDict> findList(FindDictDTO findDictDTO);

    /**
     * 根据分类查询(先查redis，找不到查数据库）
     * @param classCode 字典分类代码
     * @return 字典信息
     */
    List<SysDictDTO> findByType(String classCode);

    /**
     * 添加字典项
     * @param dictAddDTO 字典
     */
    void addDictItem(DictAddDTO dictAddDTO);

    /**
     * 添加字典项
     * @param dictUpdateDTO 字典
     */
    void updateDictItem(DictUpdateDTO dictUpdateDTO);

    /**
     * 删除字典项
     * @param id 字典ID
     */
    void removeDictItem(String id);

    /**
     * 是否存在字典项
     * @param existsQueryDTO 字典
     * @return 是否
     */
    boolean existsDict(DictExistsQueryDTO existsQueryDTO);

    /**
     * 获取字典分类树
     * @return 字典分类树
     */
    List<DictClassVO> findDictClassTree();

    /**
     * 查询字典分类树列表数据
     * @param findCatalogueVO 查询条件
     * @return list
     */
    List<DictClassVO> findDictClassList(FindClassVO findCatalogueVO);

    /**
     * 新增字典分类
     * @param dictClassAddVO 字典分类
     */
    void addDictClass(DictClassAddVO dictClassAddVO);

    /**
     * 修改字典分类
     * @param dictClassUpdateVO 字典分类
     */
    void updateDictClass(DictClassUpdateVO dictClassUpdateVO);

    /**
     * 是否存在字典分类
     * @param queryDTO 查询条件
     * @return 是否
     */
    boolean existsDictClass(DictClassExistsQueryDTO queryDTO);

    /**
     * 删除字典分类
     * @param id 主键
     */
    void removeClass(String id);
}
