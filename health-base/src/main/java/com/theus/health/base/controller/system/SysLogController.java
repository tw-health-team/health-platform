package com.theus.health.base.controller.system;

import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.common.controller.QueryController;
import com.theus.health.base.model.dto.system.log.FindLogDTO;
import com.theus.health.base.model.po.system.SysLog;
import com.theus.health.base.service.system.SysLogService;
import com.theus.health.core.bean.ResponseCode;
import com.theus.health.core.bean.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tangwei
 * @date 2019-08-24 22:43
 */
@RestController
@RequestMapping(value = "/system/log")
@Api(tags = {"日志管理"})
public class SysLogController implements QueryController<SysLog, FindLogDTO, SysLogService> {

    @Resource
    private SysLogService sysLogService;

    @Override
    public SysLogService getService() {
        return sysLogService;
    }

    @PostMapping("/remove")
    @ApiOperation("批量删除")
    @SysLogs("批量删除")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult removeList(@RequestBody @ApiParam("ID集合") List<String> logList) {
        sysLogService.remove(logList);
        return ResponseResult.e(ResponseCode.OK);
    }


}
