package com.theus.health.base.controller.system;

import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.model.dto.system.area.AreaDTO;
import com.theus.health.base.model.dto.system.area.FindAreaDTO;
import com.theus.health.base.service.system.SysAreaService;
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
 * 行政区划控制器
 *
 * @author libin
 * @date 2019-12-12 17:47
 */
@Api(tags = {"行政区划"})
@RestController
@RequestMapping("/system/area")
public class SysAreaController {

    @Resource
    private SysAreaService sysAreaService;

    @GetMapping(value = "/children/{id}")
    @ApiOperation(value = "查询区划子集")
    @SysLogs("查询区划子集")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult children(@PathVariable("id") @ApiParam("区划编码") String id) {
        return ResponseResult.e(ResponseCode.OK, sysAreaService.findChildren(id));
    }

    @PostMapping(value = {"/findTree"})
    @ApiOperation(value = "行政区划树形数据")
    @SysLogs("行政区划树形数据")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult findTree(@RequestBody @Validated @ApiParam(value = "区划查询条件") FindAreaDTO findAreaDTO) {
        return ResponseResult.e(ResponseCode.OK, sysAreaService.findTree(findAreaDTO));
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询行政区划信息")
    @SysLogs("查询行政区划信息")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult getArea(@PathVariable("id") @ApiParam("区划编码") String id) {
        return ResponseResult.e(ResponseCode.OK, sysAreaService.findById(id));
    }

    @GetMapping(value = "/parent/{id}")
    @ApiOperation(value = "通过区划编码获取上级行政区划")
    @SysLogs("通过区划编码获取上级行政区划")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult getParent(@PathVariable("id") @ApiParam("区划编码") String id) {
        return ResponseResult.e(ResponseCode.OK, sysAreaService.findParent(id));
    }

    @PostMapping(value = {"/add"})
    @ApiOperation(value = "添加区划信息")
    @SysLogs("添加区划信息")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult add(@RequestBody @Validated @ApiParam(value = "区划数据") AreaDTO areaDTO) {
        sysAreaService.add(areaDTO);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/update"})
    @ApiOperation(value = "更新区划信息")
    @SysLogs("更新区划信息")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult update(@RequestBody @Validated @ApiParam(value = "区划数据") AreaDTO areaDTO) {
        sysAreaService.update(areaDTO);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping("/remove")
    @ApiOperation("批量删除")
    @SysLogs("批量删除")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult removeList(@RequestBody @ApiParam("ID集合") List<String> idList) {
        sysAreaService.removeByIds(idList);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/remove/{id}"})
    @ApiOperation(value = "删除行政区划")
    @SysLogs("删除行政区划")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult remove(@PathVariable("id") @ApiParam("行政区划编码") String id){
        sysAreaService.removeById(id);
        return ResponseResult.e(ResponseCode.OK);
    }

    @GetMapping(value = {"/jsonFile/{id}"})
    @ApiOperation(value = "生成所有下级行政区划json文件")
    @SysLogs("生成所有下级行政区划json文件")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult createAreaJsonFile(@PathVariable("id") @ApiParam("行政区划编码") String id){
        sysAreaService.createAreaJsonFile(id);
        return ResponseResult.e(ResponseCode.OK);
    }
}
