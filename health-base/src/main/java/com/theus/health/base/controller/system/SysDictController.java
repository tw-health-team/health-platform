package com.theus.health.base.controller.system;

import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.model.dto.system.dict.FindDictDTO;
import com.theus.health.base.model.po.system.SysDict;
import com.theus.health.base.service.system.SysDictService;
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
 * 字典控制器
 * @author tangwei
 * @date 2019-10-07 22:04
 */
@Api(tags = {"字典管理"})
@RestController
@RequestMapping("/system/dict")
public class SysDictController {

	@Resource
	private SysDictService sysDictService;

	@PostMapping(value = {"/add"})
	@ApiOperation(value = "添加字典")
	@SysLogs("添加字典")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult add(@RequestBody @Validated @ApiParam(value = "用户数据") SysDict sysDict){
		sysDictService.save(sysDict);
		return ResponseResult.e(ResponseCode.OK);
	}

	@PostMapping(value = {"/update"})
	@ApiOperation(value = "更新字典")
	@SysLogs("更新字典")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult update(@RequestBody @Validated @ApiParam(value = "用户数据") SysDict sysDict){
		sysDictService.updateById(sysDict);
		return ResponseResult.e(ResponseCode.OK);
	}

	@PostMapping("/remove")
	@ApiOperation("批量删除")
	@SysLogs("批量删除")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
	public ResponseResult removeList(@RequestBody @ApiParam("ID集合") List<String> idList) {
		sysDictService.removeByIds(idList);
		return ResponseResult.e(ResponseCode.OK);
	}

	@PostMapping(value = {"/list"})
	@ApiOperation(value = "分页获取字典数据")
	@SysLogs("分页获取字典数据")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult list(@RequestBody @Validated @ApiParam(value = "字典获取过滤条件") FindDictDTO findDictDTO){
		return ResponseResult.e(ResponseCode.OK,sysDictService.findPage(findDictDTO));
	}
	
	@GetMapping(value="/findByLabel")
	@ApiOperation(value = "根据字典名称获取字典信息")
	@SysLogs("根据字典名称获取字典信息")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult findByLabel(@RequestParam String label) {
		return ResponseResult.e(ResponseCode.OK,sysDictService.findByLabel(label));
	}

	@GetMapping(value="/findByType/{type}")
	@ApiOperation(value = "根据字典分类获取字典信息")
	@SysLogs("根据字典分类获取字典信息")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult findByType(@PathVariable("type") @ApiParam("字典分类") String type) {
		return ResponseResult.e(ResponseCode.OK,sysDictService.findByType(type));
	}
}
