package com.theus.health.empi.controller;

import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.core.bean.ResponseCode;
import com.theus.health.core.bean.ResponseResult;
import com.theus.health.empi.model.dto.registerRule.JbxxZcgzAddDTO;
import com.theus.health.empi.model.dto.registerRule.JbxxZcgzUpdateDTO;
import com.theus.health.empi.service.RegisterRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 注册规则控制器
 * @author libin
 * @date 2020-3-20 15:31
 */
@Api(tags = {"注册规则"})
@RestController
@RequestMapping("registerRule")
public class RegisterRuleController {

    @Resource
    private RegisterRuleService registerRuleService;

    @PostMapping(value = {"/add"})
    @ApiOperation(value = "添加注册规则")
    @SysLogs("添加注册规则")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult add(@RequestBody @Validated @ApiParam(value = "用户数据") JbxxZcgzAddDTO jbxxZcgzAddDTO){
        registerRuleService.add(jbxxZcgzAddDTO);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/update"})
    @ApiOperation(value = "更新注册规则")
    @SysLogs("更新注册规则")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult update(@RequestBody @Validated @ApiParam(value = "用户数据") JbxxZcgzUpdateDTO jbxxZcgzUpdateDTO){
        registerRuleService.update(jbxxZcgzUpdateDTO);
        return ResponseResult.e(ResponseCode.OK);
    }

    @GetMapping(value="/findData")
    @ApiOperation(value = "查询注册规则")
    @SysLogs("查询注册规则")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult findData() {
        return ResponseResult.e(ResponseCode.OK,registerRuleService.findData());
    }
}
