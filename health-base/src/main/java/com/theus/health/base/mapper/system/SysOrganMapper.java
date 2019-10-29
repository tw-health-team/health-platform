package com.theus.health.base.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.theus.health.base.model.dto.system.organ.FindOrganDTO;
import com.theus.health.base.model.po.system.SysOrgan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tangwei
 * @date 2019-09-25 20:27
 */
@Mapper
@Repository
public interface SysOrganMapper extends BaseMapper<SysOrgan> {

    /**
     * 根据名称查找机构列表
     * @param findDeptDTO 查询条件
     * @return 机构列表
     */
    List<SysOrgan> list(@Param(value="organ") FindOrganDTO findDeptDTO);
}
