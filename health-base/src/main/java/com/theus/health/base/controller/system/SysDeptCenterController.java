package com.theus.health.base.controller.system;

import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.model.dto.system.deptCenter.DeptCenterAddDTO;
import com.theus.health.base.model.dto.system.deptCenter.DeptCenterUpdateDTO;
import com.theus.health.base.model.dto.system.deptCenter.FindDeptCenterDTO;
import com.theus.health.base.model.po.system.SysDeptCenter;
import com.theus.health.base.service.system.SysDeptCenterService;
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
 * 中心科室管理器
 * @author libin
 * @date 2019-10-24 19:43
 */
@Api(tags = {"中心科室管理"})
@RestController
@RequestMapping("/system/deptCenter")
public class SysDeptCenterController {

    @Resource
    private SysDeptCenterService sysDeptCenterService;

    @PostMapping(value = {"/add"})
    @ApiOperation(value = "添加中心科室")
    @SysLogs("添加中心科室")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult add(@RequestBody @Validated @ApiParam(value = "用户数据") DeptCenterAddDTO deptCenterAddDTO){
        sysDeptCenterService.add(deptCenterAddDTO);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/update"})
    @ApiOperation(value = "更新中心科室")
    @SysLogs("更新中心科室")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult update(@RequestBody @Validated @ApiParam(value = "用户数据") DeptCenterUpdateDTO deptCenterUpdateDTO){
        sysDeptCenterService.update(deptCenterUpdateDTO);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping("/remove")
    @ApiOperation("批量删除")
    @SysLogs("批量删除")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult removeList(@RequestBody @ApiParam("ID集合") List<String> idList) {
        sysDeptCenterService.removeByIds(idList);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/findTree"})
    @ApiOperation(value = "中心科室树形数据")
    @SysLogs("中心科室树形数据")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult findTree(@RequestBody @Validated @ApiParam(value = "中心科室获取过滤条件") FindDeptCenterDTO findDeptCenterDTO){
        return ResponseResult.e(ResponseCode.OK, sysDeptCenterService.findTree(findDeptCenterDTO));
    }

}
