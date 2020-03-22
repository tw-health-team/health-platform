package com.theus.health.empi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theus.health.core.exception.BusinessException;
import com.theus.health.empi.mapper.RegisterRuleMapper;
import com.theus.health.empi.model.dto.registerRule.JbxxZcgzAddDTO;
import com.theus.health.empi.model.dto.registerRule.JbxxZcgzUpdateDTO;
import com.theus.health.empi.model.po.JbxxZcgz;
import com.theus.health.empi.service.RegisterRuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author libin
 * @date 2020-3-20 15:46
 */
@Service
public class RegisterRuleServiceImpl extends ServiceImpl<RegisterRuleMapper, JbxxZcgz> implements RegisterRuleService {

    @Override
    public void add(JbxxZcgzAddDTO jbxxZcgzAddDTO) {
        JbxxZcgz jbxxZcgz  = new JbxxZcgz();
        BeanUtils.copyProperties(jbxxZcgzAddDTO, jbxxZcgz);
        jbxxZcgz.setCjsj(new Date());
        this.save(jbxxZcgz);
    }

    @Override
    public void update(JbxxZcgzUpdateDTO jbxxZcgzUpdateDTO) {
        JbxxZcgz jbxxZcgz = this.getById(jbxxZcgzUpdateDTO.getLsh());
        if (jbxxZcgz == null) {
            throw BusinessException.fail(
                    String.format("更新失败，不存在LSH为 %s 的注册规则", jbxxZcgzUpdateDTO.getLsh()));
        }
        BeanUtils.copyProperties(jbxxZcgzUpdateDTO, jbxxZcgz);
        jbxxZcgz.setCjsj(new Date());
        try {
            this.updateById(jbxxZcgz);
        } catch (BusinessException e) {
            throw BusinessException.fail(e.getMsg(), e);
        } catch (Exception e) {
            throw BusinessException.fail("注册规则信息更新失败", e);
        }
    }

    @Override
    public JbxxZcgz findData() {
        List<JbxxZcgz> jbxxZcgzList = this.list();
        if (jbxxZcgzList != null && jbxxZcgzList.size() > 0) {
            return jbxxZcgzList.get(0);
        } else {
            return new JbxxZcgz();
        }
    }
}
