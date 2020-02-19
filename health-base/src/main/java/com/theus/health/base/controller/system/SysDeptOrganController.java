package com.theus.health.base.controller.system;

import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.model.dto.system.deptOrgan.DeptOrganAddDTO;
import com.theus.health.base.model.dto.system.deptOrgan.DeptOrganUpdateDTO;
import com.theus.health.base.model.dto.system.deptOrgan.FindDeptOrganDTO;
import com.theus.health.base.model.po.system.SysDeptOrgan;
import com.theus.health.base.service.system.SysDeptOrganService;
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
 * @author libin
 * @date 2019-11-4 17:58
 */

@Api(tags = {"院内科室管理"})
@RestController
@RequestMapping("/system/deptOrgan")
public class SysDeptOrganController {

    @Resource
    private SysDeptOrganService sysDeptOrganService;

    @PostMapping(value = {"/add"})
    @ApiOperation(value = "添加院内科室")
    @SysLogs("添加院内科室")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult add(@RequestBody @Validated @ApiParam(value = "用户数据")DeptOrganAddDTO deptOrganAddDTO){
        sysDeptOrganService.add(deptOrganAddDTO);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/update"})
    @ApiOperation(value = "更新院内科室")
    @SysLogs("更新院内科室")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult update(@RequestBody @Validated @ApiParam(value = "用户数据") DeptOrganUpdateDTO deptOrganUpdateDTO){
        sysDeptOrganService.update(deptOrganUpdateDTO);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping("/remove")
    @ApiOperation("批量删除")
    @SysLogs("批量删除")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult removeList(@RequestBody @ApiParam("ID集合") List<String> idList) {
        sysDeptOrganService.removeByIds(idList);
        return ResponseResult.e(ResponseCode.OK);
    }


    @PostMapping(value = {"/findPage"})
    @ApiOperation(value = "院内科室树形数据")
    @SysLogs("院内科室树形数据")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult findPage(@RequestBody @Validated @ApiParam(value = "院内科室获取过滤条件") FindDeptOrganDTO findDeptOrganDTO){
        return ResponseResult.e(ResponseCode.OK, sysDeptOrganService.findPage(findDeptOrganDTO));
    }
}
