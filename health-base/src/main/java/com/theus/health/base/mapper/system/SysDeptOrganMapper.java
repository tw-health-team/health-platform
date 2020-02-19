package com.theus.health.base.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.theus.health.base.model.dto.system.deptOrgan.FindDeptOrganDTO;
import com.theus.health.base.model.po.system.SysDeptOrgan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author libin
 * @date 2019-11-4 14:57
 */

@Mapper
@Repository
public interface SysDeptOrganMapper extends BaseMapper<SysDeptOrgan> {

    /**
     * 查询院内科室
     * @param findDeptOrganDTO 查询条件
     * @return 院内科室list
     */
    List<SysDeptOrgan> findPage(IPage<SysDeptOrgan> deptOrganPagePage, @Param("deptOrgan") FindDeptOrganDTO findDeptOrganDTO);
}
