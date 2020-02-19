package com.theus.health.base.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.theus.health.base.model.dto.system.area.FindAreaDTO;
import com.theus.health.base.model.po.system.SysArea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author libin
 * @date 2019-12-12 15:02
 */
@Mapper
@Repository
public interface SysAreaMapper extends BaseMapper<SysArea> {

    List<SysArea> findTree(@Param(value="area") FindAreaDTO findAreaDTO);
}
