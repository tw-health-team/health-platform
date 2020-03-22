package com.theus.health.empi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.theus.health.empi.model.po.JbxxZcgz;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author libin
 * @date 2020-3-20 15:55
 */
@Mapper
@Repository
public interface RegisterRuleMapper  extends BaseMapper<JbxxZcgz> {
}
