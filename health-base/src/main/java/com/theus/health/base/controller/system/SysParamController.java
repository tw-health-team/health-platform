package com.theus.health.base.controller.system;

import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.common.controller.QueryController;
import com.theus.health.base.model.dto.system.param.FindParamDTO;
import com.theus.health.base.model.po.system.SysParam;
import com.theus.health.base.service.system.SysParamService;
import com.theus.health.core.bean.ResponseCode;
import com.theus.health.core.bean.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author zgx
 * @Date 2019/10/25 13:47
 * @Version 1.0
 */
@Api(tags = {"参数管理"})
@RestController
@RequestMapping("/system/param")
public class SysParamController implements QueryController<SysParam, FindParamDTO, SysParamService> {

    @Resource
    private SysParamService sysParamService;

    @Override
    public SysParamService getService() {
        return sysParamService;
    }

    @PostMapping(value = {"/add"})
    @ApiOperation(value = "添加参数")
    @SysLogs("添加参数")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult add(@RequestBody @Validated @ApiParam(value = "参数数据") SysParam sysParam){
        sysParamService.save(sysParam);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/update"})
    @ApiOperation(value = "更新参数")
    @SysLogs("更新参数")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult update(@RequestBody @Validated @ApiParam(value = "参数数据") SysParam sysParam){
        sysParamService.updateById(sysParam);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping("/remove")
    @ApiOperation("批量删除")
    @SysLogs("批量删除")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult removeList(@RequestBody @ApiParam("ID集合") List<String> idList) {
        sysParamService.removeByIds(idList);
        return ResponseResult.e(ResponseCode.OK);
    }
}