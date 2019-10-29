package com.theus.health.base.service.system.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theus.health.base.mapper.system.SysParamMapper;
import com.theus.health.base.model.dto.system.param.FindParamDTO;
import com.theus.health.base.model.po.system.SysParam;
import com.theus.health.base.service.system.SysParamService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @Description TODO
 * @Author zgx
 * @Date 2019/10/27 20:05
 * @Version 1.0
 */
@Service
public class SysParamServiceImpl extends ServiceImpl<SysParamMapper, SysParam> implements SysParamService {


    @Override
    public IPage<SysParam> list(FindParamDTO findParamDTO) {
        QueryWrapper<SysParam> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(findParamDTO.getName())) {
            wrapper.like("name",findParamDTO.getName())
                    .orderBy(true, findParamDTO.getAsc(), "create_time");
        }
        return this.page(new Page<>(findParamDTO.getPageNum(), findParamDTO.getPageSize()), wrapper);
    }
}