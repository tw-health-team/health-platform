package com.theus.health.record.controller;

import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.common.controller.QueryController;
import com.theus.health.core.bean.ResponseCode;
import com.theus.health.core.bean.ResponseResult;
import com.theus.health.record.model.dto.FindPersonalInfoDTO;
import com.theus.health.record.model.dto.FindPersonalListDTO;
import com.theus.health.record.model.dto.PersonalInfoDTO;
import com.theus.health.record.service.PersonalInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 个人基本信息控制器
 *
 * @author tangwei
 * @date 2020-03-25 15:07
 */
@Api(tags = {"个人基本信息"})
@RestController
@RequestMapping("/ehr/personalInfo")
public class PersonalInfoController implements QueryController<PersonalInfoDTO, FindPersonalListDTO, PersonalInfoService> {
    @Resource
    private PersonalInfoService personalInfoService;

    @Override
    public PersonalInfoService getService() {
        return personalInfoService;
    }

    @PostMapping(value = "/record")
    @ApiOperation(value = "查询居民个人基本信息")
    @SysLogs("查询居民个人基本信息")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult getRecord(@RequestBody @Validated @ApiParam(value = "查询条件") FindPersonalInfoDTO findPersonalInfoDTO) {
        return ResponseResult.e(ResponseCode.OK, personalInfoService.getRecord(findPersonalInfoDTO));
    }

    @PostMapping(value = {"/remove/{personalId}"})
    @ApiOperation(value = "删除个人基本信息")
    @SysLogs("删除个人基本信息")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult remove(@PathVariable("personalId") @ApiParam(value = "个人档案标识号") String personalId) {
        personalInfoService.removeById(personalId);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/add"})
    @ApiOperation(value = "添加个人基本信息")
    @SysLogs("添加个人基本信息")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult add(@RequestBody @Validated @ApiParam(value = "个人基本信息") PersonalInfoDTO personalInfoDTO){
        personalInfoService.addPersonalInfo(personalInfoDTO);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/update"})
    @ApiOperation(value = "更新个人基本信息")
    @SysLogs("更新个人基本信息")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token")
    public ResponseResult update(@RequestBody @Validated @ApiParam(value = "个人基本信息") PersonalInfoDTO personalInfoDTO){
        personalInfoService.updatePersonalInfo(personalInfoDTO);
        return ResponseResult.e(ResponseCode.OK);
    }
}
