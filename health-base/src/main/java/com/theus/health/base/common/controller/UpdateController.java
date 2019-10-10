package com.theus.health.base.common.controller;

import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.common.service.UpdateService;
import com.theus.health.core.bean.ResponseCode;
import com.theus.health.core.bean.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author tangwei
 * @date 2019-08-17 12:04
 */
public interface UpdateController<UID,UD,S extends UpdateService<UID,UD>> {

    /**
     * 获取服务接口，子接口实现
     * @return 接口实例
     */
    S getService();

    /**
     * 通用更新接口
     * @param id 主键
     * @param updateDTO 更新实体
     * @return 结果
     */
    @PostMapping("/update/{id}")
    @ApiOperation(value = "更新指定ID对象的信息")
    @SysLogs("更新指定ID对象的信息")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token",required = true)
    default ResponseResult update(@PathVariable("id") UID id, @RequestBody @Validated UD updateDTO){
        getService().update(id,updateDTO);
        return ResponseResult.e(ResponseCode.OK);
    }

}
