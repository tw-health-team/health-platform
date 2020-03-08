package com.theus.health.base.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.theus.health.base.model.dto.system.area.FindAreaDTO;
import com.theus.health.base.model.po.system.SysArea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @author tangwei
 * @date 2020-02-23 14:20
 */
@Mapper
@Repository
public interface SysAreaMapper extends BaseMapper<SysArea> {

    /**
     * 获取行政区划列表
     * @param findAreaDTO 查询条件
     * @return 区划list
     */
    List<SysArea> findAreas(@Param(value="area") FindAreaDTO findAreaDTO);

    /**
     * 获取下一级行政区划
     * @param id 区划id
     * @return 区划list
     */
    List<SysArea> findChildren(String id);

    /**
     * 获取下一级行政区划
     * @param id 区划id
     * @return HashMap-List
     */
    List<HashMap<String, Object>> getAreaHashMap(String id);

    /**
     * 获取省市区数据（不足6位补0）
     * @return HashMap-List
     */
    List<HashMap<String, Object>> getLevel123Areas();

    /**
     * 获取所有区县或省市下的所有区县
     * @param id 顶级区划id、空、省市区划id
     * @return HashMap-List
     */
    List<HashMap<String, Object>> getDistricts(@Param(value="id") String id);
}
