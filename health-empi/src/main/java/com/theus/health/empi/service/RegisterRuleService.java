package com.theus.health.empi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.theus.health.empi.model.dto.registerRule.JbxxZcgzAddDTO;
import com.theus.health.empi.model.dto.registerRule.JbxxZcgzUpdateDTO;
import com.theus.health.empi.model.po.JbxxZcgz;
import java.util.List;

/**
 * @author libin
 * @date 2020-3-20 15:45
 */
public interface RegisterRuleService extends IService<JbxxZcgz> {

    /**
     * 新增注册规则
     *
     * @param jbxxZcgzAddDTO
     */
    void add(JbxxZcgzAddDTO jbxxZcgzAddDTO);

    /**
     * 修改注册规则
     *
     * @param jbxxZcgzUpdateDTO
     */
    void update(JbxxZcgzUpdateDTO jbxxZcgzUpdateDTO);

    JbxxZcgz findData();
}
