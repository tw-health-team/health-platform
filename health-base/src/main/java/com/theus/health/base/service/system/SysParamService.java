package com.theus.health.base.service.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.theus.health.base.common.service.QueryService;
import com.theus.health.base.model.dto.system.param.FindParamDTO;
import com.theus.health.base.model.po.system.SysParam;

import java.util.List;

/**
 * @Description TODO
 * @Author zgx
 * @Date 2019/10/27 20:00
 * @Version 1.0
 */
public interface SysParamService extends IService<SysParam>, QueryService<SysParam, FindParamDTO> {
    /**
     * 查询列表（分页）
     *
     * @param findParamDTO 条件
     * @return 日志分页信息
     */
    @Override
    IPage<SysParam> list(FindParamDTO findParamDTO);
}