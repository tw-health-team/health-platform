package com.theus.health.base.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theus.health.base.model.dto.system.area.AreaAddDTO;
import com.theus.health.base.model.dto.system.area.AreaUpdateDTO;
import com.theus.health.base.model.dto.system.area.FindAreaDTO;
import com.theus.health.base.model.po.system.SysArea;

import java.util.List;

/**
 * @author libin
 * @date 2019-12-11 20:48
 */
public interface SysAreaService extends IService<SysArea> {
    /**
     * 根据地址编码查找地址信息
     * @param id 地址编码
     * @return 地址信息
     */
    SysArea findById(String id);

    /**
     * 查询地址列表
     * @param findAreaDTO
     * @return
     */
    List<SysArea> findTree(FindAreaDTO findAreaDTO);

    /**
     * 新增地址信息
     * @param areaAddDTO
     */
    void add(AreaAddDTO areaAddDTO);

    /**
     * 修改地址信息
     * @param areaUpdateDTO
     */
    void update(AreaUpdateDTO areaUpdateDTO);
}
