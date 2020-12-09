package com.theus.health.base.controller.system;

import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.model.dto.system.organ.OrganAddDTO;
import com.theus.health.base.model.dto.system.organ.OrganUpdateDTO;
import com.theus.health.base.model.dto.system.organ.FindOrganDTO;
import com.theus.health.base.model.po.system.SysOrgan;
import com.theus.health.base.service.system.SysOrganService;
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
 * 机构控制器
 * @author tangwei
 * @date 2019-09-25 20:41
 */
@Api(tags = {"机构管理"})
@RestController
@RequestMapping("/system/organ")
public class SysOrganController {

	@Resource
	private SysOrganService sysOrganService;

	@PostMapping(value = {"/get/id/{id}"})
	@ApiOperation(value = "根据ID获取机构信息")
	@SysLogs("根据ID获取机构信息")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
	public ResponseResult<SysOrgan> getOrgan(@PathVariable("id") @ApiParam(value = "机构ID") String id) {
		return ResponseResult.e(ResponseCode.OK,sysOrganService.getById(id));
	}

	@PostMapping("/remove")
	@ApiOperation("批量删除")
	@SysLogs("批量删除")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
	public ResponseResult<String> removeList(@RequestBody @ApiParam("ID集合") List<String> idList) {
		sysOrganService.removeByIds(idList);
		return ResponseResult.e(ResponseCode.OK);
	}

	@PostMapping(value = {"/add"})
	@ApiOperation(value = "添加机构")
	@SysLogs("添加用户")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult<String> add(@RequestBody @Validated @ApiParam(value = "机构数据") OrganAddDTO addDTO){
		sysOrganService.add(addDTO);
		return ResponseResult.e(ResponseCode.OK);
	}

	@PostMapping(value = {"/update/{id}"})
	@ApiOperation(value = "更新机构")
	@SysLogs("更新机构")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult<String> update(@PathVariable("id") @ApiParam(value = "机构标识ID") String id,
								 @RequestBody @Validated @ApiParam(value = "机构数据") OrganUpdateDTO updateDTO){
		sysOrganService.update(id,updateDTO);
		return ResponseResult.e(ResponseCode.OK);
	}

	@GetMapping(value="/treeList")
	@ApiOperation(value = "查询机构树")
	@SysLogs("查询机构树")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult<List<SysOrgan>> findTree() {
		return ResponseResult.e(ResponseCode.OK,sysOrganService.findTree(new FindOrganDTO()));
	}

	@PostMapping(value="/list")
	@ApiOperation(value = "查询机构列表")
	@SysLogs("查询机构列表")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult<List<SysOrgan>> list(@RequestBody @Validated @ApiParam(value = "机构获取过滤条件") FindOrganDTO findDeptDTO) {
		return ResponseResult.e(ResponseCode.OK,sysOrganService.findTree(findDeptDTO));
	}
}
