package com.theus.health.base.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.theus.health.base.model.dto.system.deptCenter.FindDeptCenterDTO;
import com.theus.health.base.model.po.system.SysDeptCenter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author libin
 * @date 2019-10-24 20:57
 */
@Mapper
@Repository
public interface SysDeptCenterMapper extends BaseMapper<SysDeptCenter> {

    List<SysDeptCenter> findTree(@Param(value="deptCenter") FindDeptCenterDTO findDeptCenterDTO);
}
