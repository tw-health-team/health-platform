package com.theus.health.base.service.system.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theus.health.base.model.po.system.SysLog;
import com.theus.health.base.mapper.system.SysLogMapper;
import com.theus.health.base.model.dto.system.log.FindLogDTO;
import com.theus.health.base.service.system.SysLogService;
import com.theus.health.core.bean.ResponseCode;
import com.theus.health.core.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tangwei
 * @date 2019-07-28 21:11
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Override
    public IPage<SysLog> list(FindLogDTO findLogDTO) {
        QueryWrapper<SysLog> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(findLogDTO.getName())) {
            wrapper.eq("username",findLogDTO.getName())
                    .orderBy(true, findLogDTO.getAsc(), "create_date");
        }
        return this.page(new Page<>(findLogDTO.getPageNum(), findLogDTO.getPageSize()), wrapper);
    }

    @Override
    public void remove(List<String> idList) {
        try {
            this.removeByIds(idList);
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.FAIL.code, "批量删除日志失败", e);
        }
    }
}
