package com.theus.health.base.controller.system;

import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.model.dto.system.user.FindUserDTO;
import com.theus.health.base.model.dto.system.user.ResetPasswordDTO;
import com.theus.health.base.model.dto.system.user.UserAddDTO;
import com.theus.health.base.model.dto.system.user.UserUpdateDTO;
import com.theus.health.base.service.system.SysUserService;
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
 * @author tangwei
 * @date 2019/5/5 14:02
 */
@Api(tags = {"用户管理"})
@RestController
@RequestMapping("/system/user")
public class SysUserController {
    @Resource
    SysUserService sysUserService;

    @PostMapping(value = {"/list"})
    @ApiOperation(value = "分页获取用户数据")
    @SysLogs("分页获取用户数据")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult get(@RequestBody @Validated @ApiParam(value = "用户获取过滤条件") FindUserDTO findUserDTO){
        return ResponseResult.e(ResponseCode.OK,sysUserService.findPage(findUserDTO));
    }

    @PostMapping(value = {"/get/id/{id}"})
    @ApiOperation(value = "根据ID获取用户信息")
    @SysLogs("根据ID获取用户信息")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult getUser(@PathVariable("id") @ApiParam(value = "用户ID") String id) {
        return ResponseResult.e(ResponseCode.OK,sysUserService.findUserById(id,true));
    }

    @PostMapping(value = {"/lock/{id}"})
    @ApiOperation(value = "锁定用户")
    @SysLogs("锁定用户")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult lock(@PathVariable("id") @ApiParam(value = "用户标识ID") String id){
        sysUserService.statusChange(id, 0);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/unlock/{id}"})
    @ApiOperation(value = "解锁用户")
    @SysLogs("解锁用户")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult unlock(@PathVariable("id") @ApiParam(value = "用户标识ID") String id){
        sysUserService.statusChange(id, 1);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/remove/{id}"})
    @ApiOperation(value = "删除用户")
    @SysLogs("删除用户")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult remove(@PathVariable("id") @ApiParam(value = "用户标识ID") String id){
        sysUserService.removeUser(id);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/add"})
    @ApiOperation(value = "添加用户")
    @SysLogs("添加用户")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult add(@RequestBody @Validated @ApiParam(value = "用户数据") UserAddDTO addDTO){
        sysUserService.add(addDTO);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/update/{id}"})
    @ApiOperation(value = "更新用户")
    @SysLogs("更新用户")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult update(@PathVariable("id") @ApiParam(value = "用户标识ID") String id,
                                 @RequestBody @Validated @ApiParam(value = "用户数据") UserUpdateDTO updateDTO){
        sysUserService.update(id,updateDTO);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/update"})
    @ApiOperation(value = "更新用户")
    @SysLogs("更新用户")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult updateNew(@RequestBody @Validated @ApiParam(value = "用户数据") UserUpdateDTO updateDTO){
        sysUserService.update(updateDTO.getId(),updateDTO);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/reset-password"})
    @ApiOperation(value = "重置密码")
    @SysLogs("重置密码")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult resetPassword(@RequestBody
                                        @Validated @ApiParam(value = "用户及密码数据") ResetPasswordDTO dto){
        sysUserService.resetPassword(dto);
        return ResponseResult.e(ResponseCode.OK);
    }

    @GetMapping(value="/permissions/{username}")
    @ApiOperation(value = "获取用户操作权限")
    @SysLogs("获取用户操作权限")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult permissions(@PathVariable("username") @ApiParam("用户名") String username) {
        return ResponseResult.e(ResponseCode.OK,sysUserService.findPermissions(username));
    }

    @GetMapping(value="/userInfo/{username}")
    @ApiOperation(value = "获取用户操作权限")
    @SysLogs("获取用户操作权限")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult getUserByName(@PathVariable("username") @ApiParam("用户名") String username) {
        return ResponseResult.e(ResponseCode.OK,sysUserService.getCacheUser(username));
    }
}
