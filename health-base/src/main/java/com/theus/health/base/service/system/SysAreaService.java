package com.theus.health.base.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theus.health.base.model.dto.system.area.AreaDTO;
import com.theus.health.base.model.dto.system.area.FindAreaDTO;
import com.theus.health.base.model.po.system.SysArea;
import com.theus.health.base.model.vo.area.AreaTreeVO;

import java.util.List;

/**
 * 行政区划服务接口
 * 说明：
 * 1、通过省来查区划
 * 2、查询省下所有的区划：使用接口findChildren一级一级获取（直接获取某个省下的区划树速度很慢）
 * 3、带过滤条件的查询：使用接口findTree直接获取区划树
 * @author libin
 * @date 2019-12-11 20:48
 */
public interface SysAreaService extends IService<SysArea> {

    /**
     * 获取行政区划子集
     * @param id 区划编码
     * @return 子集
     */
    List<AreaTreeVO> findChildren(String id);

    /**
     * 查询行政区划树
     * @param findAreaDTO 查询条件
     * @return 树
     */
    List<AreaTreeVO> findTree(FindAreaDTO findAreaDTO);

    /**
     * 根据区划编码查找区划信息
     * @param id 区划编码
     * @return 区划信息
     */
    AreaTreeVO findById(String id);

    /**
     * 根据区划编码查找上级区划信息
     * @param id 区划编码
     * @return 区划信息
     */
    AreaTreeVO findParent(String id);

    /**
     * 新增区划信息
     * @param areaDTO 区划信息
     */
    void add(AreaDTO areaDTO);

    /**
     * 修改区划信息
     * @param areaDTO 区划信息
     */
    void update(AreaDTO areaDTO);

    /**
     * 根据编码生成所有下级行政区划json文件
     * 1、data.json：包含所有省市区县
     * 2、[区县编码].json：包含该区县下所有街道
     * 3、[街道编码].json：包含该街道下所有居委
     * @param id 区划编码
     */
    void createAreaJsonFile(String id);
}
