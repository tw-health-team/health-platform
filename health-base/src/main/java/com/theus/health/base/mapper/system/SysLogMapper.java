package com.theus.health.base.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.theus.health.base.model.po.system.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author tangwei
 * @date 2019-07-28 21:29
 */
@Mapper
@Repository
public interface SysLogMapper extends BaseMapper<SysLog> {
}
