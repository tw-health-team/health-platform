package com.theus.health.base.controller.system;

import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.model.dto.system.dict.*;
import com.theus.health.base.model.vo.dict.DictClassAddVO;
import com.theus.health.base.model.vo.dict.DictClassUpdateVO;
import com.theus.health.base.model.vo.dict.FindClassVO;
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

	@GetMapping(value = {"/classTree"})
	@ApiOperation(value = "获取字典分类树")
	@SysLogs("获取字典分类树")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult findDictClassTree(){
		return ResponseResult.e(ResponseCode.OK,sysDictService.findDictClassTree());
	}

	@GetMapping(value="/items/{classCode}")
	@ApiOperation(value = "根据字典分类代码获取字典项信息")
	@SysLogs("根据字典分类代码获取字典项信息")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult findByType(@PathVariable("classCode") @ApiParam("字典分类代码") String classCode) {
		return ResponseResult.e(ResponseCode.OK,sysDictService.findByType(classCode));
	}

	@PostMapping(value = {"/list"})
	@ApiOperation(value = "获取字典数据")
	@SysLogs("获取字典数据")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult list(@RequestBody @Validated @ApiParam(value = "字典获取过滤条件") FindDictDTO findDictDTO){
		return ResponseResult.e(ResponseCode.OK,sysDictService.findList(findDictDTO));
	}

	@PostMapping(value = {"/add"})
	@ApiOperation(value = "添加字典")
	@SysLogs("添加字典")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult add(@RequestBody @Validated @ApiParam(value = "字典新增数据") DictAddDTO dictAddDTO){
		sysDictService.addDictItem(dictAddDTO);
		return ResponseResult.e(ResponseCode.OK);
	}

	@PostMapping(value = {"/update"})
	@ApiOperation(value = "更新字典")
	@SysLogs("更新字典")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult update(@RequestBody @Validated @ApiParam(value = "字典更新数据") DictUpdateDTO dictUpdateDTO){
		sysDictService.updateDictItem(dictUpdateDTO);
		return ResponseResult.e(ResponseCode.OK);
	}

	@PostMapping("/remove/{id}")
	@ApiOperation("删除字典项")
	@SysLogs("删除字典项")
	@ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
	public ResponseResult remove(@PathVariable("id") @ApiParam("字典项ID") String id) {
		sysDictService.removeDictItem(id);
		return ResponseResult.e(ResponseCode.OK);
	}

	@PostMapping(value = {"/item"})
	@ApiOperation(value = "判断字典项是否存在")
	@SysLogs("判断字典项是否存在")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult existsDict(@RequestBody @Validated @ApiParam(value = "字典数据") DictExistsQueryDTO existsQueryDTO){
		return ResponseResult.e(ResponseCode.OK,sysDictService.existsDict(existsQueryDTO));
	}

	@PostMapping(value = {"/classList"})
	@ApiOperation(value = "获取字典分类列表")
	@SysLogs("获取字典分类列表")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult findDictClassList(@RequestBody @Validated @ApiParam(value = "字典分类查询条件") FindClassVO findCatalogueVO){
		return ResponseResult.e(ResponseCode.OK,sysDictService.findDictClassList(findCatalogueVO));
	}

	@PostMapping(value = {"/addClass"})
	@ApiOperation(value = "添加字典分类")
	@SysLogs("添加字典分类")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult addClass(@RequestBody @Validated @ApiParam(value = "字典分类新增数据") DictClassAddVO dictClassAddVO){
		sysDictService.addDictClass(dictClassAddVO);
		return ResponseResult.e(ResponseCode.OK);
	}

	@PostMapping(value = {"/updateClass"})
	@ApiOperation(value = "更新字典分类")
	@SysLogs("更新字典分类")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult updateClass(@RequestBody @Validated @ApiParam(value = "字典分类更新数据") DictClassUpdateVO dictClassUpdateVO){
		sysDictService.updateDictClass(dictClassUpdateVO);
		return ResponseResult.e(ResponseCode.OK);
	}

	@PostMapping(value = {"/removeClass/{id}"})
	@ApiOperation(value = "删除字典分类")
	@SysLogs("删除字典分类")
	@ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
	public ResponseResult removeClass(@PathVariable("id") @ApiParam("字典分类ID") String id){
		sysDictService.removeClass(id);
		return ResponseResult.e(ResponseCode.OK);
	}
}
