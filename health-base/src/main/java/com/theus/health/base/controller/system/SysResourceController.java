package com.theus.health.base.controller.system;

import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.model.dto.system.resource.ResourceDTO;
import com.theus.health.base.service.system.SysResourceService;
import com.theus.health.core.bean.ResponseCode;
import com.theus.health.core.bean.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author tangwei
 * @date 2019-08-24 22:31
 */
@RestController
@RequestMapping(value = "/system/resource")
@Api(tags = {"资源管理"})
public class SysResourceController {

    @Resource
    private SysResourceService resourceService;

    @PostMapping(value = {"/list"})
    @ApiOperation(value = "获取所有的资源列表")
    @SysLogs("获取所有的资源列表")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult list(){
        return ResponseResult.e(ResponseCode.OK,resourceService.treeList());
    }

    @PostMapping(value = {"/add"})
    @ApiOperation(value = "添加资源")
    @SysLogs("添加资源")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult add(@RequestBody @Validated @ApiParam("资源数据") ResourceDTO dto){
        resourceService.add(dto);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/update/{id}"})
    @ApiOperation(value = "添加资源")
    @SysLogs("添加资源")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult update(@PathVariable("id") @ApiParam("资源ID") String id,
                                 @RequestBody @Validated @ApiParam("资源数据") ResourceDTO dto){
        resourceService.update(id,dto);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/remove/{id}"})
    @ApiOperation(value = "删除资源")
    @SysLogs("删除资源")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult remove(@PathVariable("id") @ApiParam("资源ID") String id){
        resourceService.remove(id);
        return ResponseResult.e(ResponseCode.OK);
    }

    @GetMapping(value="/navTree/{username}")
    @ApiOperation(value = "获取用户菜单树")
    @SysLogs("获取用户菜单树")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult navTree(@PathVariable("username") @ApiParam("用户名") String username) {
        return ResponseResult.e(ResponseCode.OK,resourceService.findTree(username, 1));
    }
}
