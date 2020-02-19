package com.theus.health.base.controller.system;

import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.model.dto.system.area.AreaAddDTO;
import com.theus.health.base.model.dto.system.area.AreaUpdateDTO;
import com.theus.health.base.model.dto.system.area.FindAreaDTO;
import com.theus.health.base.service.system.SysAreaService;
import com.theus.health.core.bean.ResponseCode;
import com.theus.health.core.bean.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 地址管理器
 * @author libin
 * @date 2019-12-12 17:47
 */

@Api(tags = {"地址管理"})
@RestController
@RequestMapping("/system/area")
public class SysAreaController {

    @Resource
    private SysAreaService sysAreaService;

    @PostMapping(value = {"/add"})
    @ApiOperation(value = "添加地址信息")
    @SysLogs("添加地址信息")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult add(@RequestBody @Validated @ApiParam(value = "用户数据") AreaAddDTO areaAddDTO){
        sysAreaService.add(areaAddDTO);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/update"})
    @ApiOperation(value = "更新地址信息")
    @SysLogs("更新地址信息")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult update(@RequestBody @Validated @ApiParam(value = "用户数据") AreaUpdateDTO areaUpdateDTO){
        sysAreaService.update(areaUpdateDTO);
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

    @PostMapping(value = {"/findTree"})
    @ApiOperation(value = "地址树形数据")
    @SysLogs("地址树形数据")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult findTree(@RequestBody @Validated @ApiParam(value = "地址获取过滤条件") FindAreaDTO findAreaDTO){
        return ResponseResult.e(ResponseCode.OK, sysAreaService.findTree(findAreaDTO));
    }
}
