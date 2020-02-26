package com.theus.health.base.controller.system;

import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.common.controller.CrudController;
import com.theus.health.base.model.po.system.SysRole;
import com.theus.health.base.model.dto.system.role.FindRoleDTO;
import com.theus.health.base.model.dto.system.role.RoleAddDTO;
import com.theus.health.base.model.dto.system.role.RoleUpdateDTO;
import com.theus.health.base.model.po.system.SysRoleResource;
import com.theus.health.base.service.system.SysRoleService;
import com.theus.health.core.bean.ResponseCode;
import com.theus.health.core.bean.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tangwei
 * @date 2019-08-17 11:41
 */
@RestController
@RequestMapping(value = {"/system/role"})
@Api(tags = {"角色管理"})
public class SysRoleController implements CrudController<SysRole, RoleAddDTO, RoleUpdateDTO,String, FindRoleDTO,SysRoleService> {

    private final SysRoleService sysRoleService;

    @Autowired
    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Override
    public SysRoleService getService() {
        return sysRoleService;
    }

    @GetMapping(value="/all")
    @ApiOperation(value = "查询所有角色")
    @SysLogs("查询所有角色")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult findAll() {
        return ResponseResult.e(ResponseCode.OK,getService().list());
    }

    @GetMapping(value="/roleResource/{roleId}")
    @ApiOperation(value = "查询角色资源")
    @SysLogs("查询角色资源")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult roleResource(@PathVariable("roleId") @ApiParam("角色ID") String roleId) {
        return ResponseResult.e(ResponseCode.OK,getService().findRoleResource(roleId));
    }

    @PostMapping(value="/saveRoleResource")
    @ApiOperation(value = "保存角色资源")
    @SysLogs("保存角色资源")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult saveRoleResource(@RequestBody List<SysRoleResource> records) {
        getService().saveRoleResource(records);
        return ResponseResult.e(ResponseCode.OK);
    }
}
