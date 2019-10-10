package com.theus.health.base.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.theus.health.base.model.po.system.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author tangwei
 * @date 2019-07-28 15:25
 */
@Mapper
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {
}
