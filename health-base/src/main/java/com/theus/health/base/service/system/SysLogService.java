package com.theus.health.base.service.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.theus.health.base.common.service.QueryService;
import com.theus.health.base.model.po.system.SysLog;
import com.theus.health.base.model.dto.system.log.FindLogDTO;

import java.util.List;

/**
 * @author tangwei
 * @date 2019-07-28 21:11
 */
public interface SysLogService extends IService<SysLog>, QueryService<SysLog, FindLogDTO> {

    /**
     * 查询列表（分页）
     *
     * @param findLogDTO 条件
     * @return 日志分页信息
     */
    @Override
    IPage<SysLog> list(FindLogDTO findLogDTO);

    /**
     * 批量删除日志
     *
     * @param idList 主键list
     */
    void remove(List<String> idList);
}
