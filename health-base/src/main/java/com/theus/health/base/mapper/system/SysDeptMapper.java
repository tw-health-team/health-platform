package com.theus.health.base.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.theus.health.base.model.po.system.SysDept;
import com.theus.health.base.model.po.system.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author tangwei
 * @date 2019-09-25 20:27
 */
@Mapper
@Repository
public interface SysDeptMapper extends BaseMapper<SysDept> {

}
