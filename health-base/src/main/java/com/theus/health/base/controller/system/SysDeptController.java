package com.theus.health.base.controller.system;

import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.model.dto.system.dept.DeptAddDTO;
import com.theus.health.base.model.dto.system.dept.DeptUpdateDTO;
import com.theus.health.base.model.dto.system.dept.FindDeptDTO;
import com.theus.health.base.service.system.SysDeptService;
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
@RequestMapping("/system/dept")
public class SysDeptController {

	@Resource
	private SysDeptService sysDeptService;

	@PostMapping(value = {"/get/id/{id}"})
	@ApiOperation(value = "根据ID获取机构信息")
	@SysLogs("根据ID获取机构信息")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
	public ResponseResult getDept(@PathVariable("id") @ApiParam(value = "机构ID") String id) {
		return ResponseResult.e(ResponseCode.OK,sysDeptService.getById(id));
	}

	@PostMapping("/remove")
	@ApiOperation("批量删除")
	@SysLogs("批量删除")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
	public ResponseResult removeList(@RequestBody @ApiParam("ID集合") List<String> idList) {
		sysDeptService.removeByIds(idList);
		return ResponseResult.e(ResponseCode.OK);
	}

	@PostMapping(value = {"/add"})
	@ApiOperation(value = "添加机构")
	@SysLogs("添加用户")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult add(@RequestBody @Validated @ApiParam(value = "机构数据") DeptAddDTO addDTO){
		sysDeptService.add(addDTO);
		return ResponseResult.e(ResponseCode.OK);
	}

	@PostMapping(value = {"/update/{id}"})
	@ApiOperation(value = "更新机构")
	@SysLogs("更新机构")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult update(@PathVariable("id") @ApiParam(value = "机构标识ID") String id,
								 @RequestBody @Validated @ApiParam(value = "机构数据") DeptUpdateDTO updateDTO){
		sysDeptService.update(id,updateDTO);
		return ResponseResult.e(ResponseCode.OK);
	}

	@GetMapping(value="/treeList")
	@ApiOperation(value = "查询机构树")
	@SysLogs("查询机构树")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult findTree() {
		return ResponseResult.e(ResponseCode.OK,sysDeptService.findTree(new FindDeptDTO()));
	}

	@PostMapping(value="/list")
	@ApiOperation(value = "查询机构列表")
	@SysLogs("查询机构列表")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult list(@RequestBody @Validated @ApiParam(value = "机构获取过滤条件") FindDeptDTO findDeptDTO) {
		return ResponseResult.e(ResponseCode.OK,sysDeptService.findTree(findDeptDTO));
	}
}
