package com.theus.health.base.common.controller;

import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.common.service.DeleteService;
import com.theus.health.core.bean.ResponseCode;
import com.theus.health.core.bean.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author tangwei
 * @date 2019-08-17 12:04
 */
public interface DeleteController<UID,S extends DeleteService<UID>> {

    /**
     * 获取服务接口，子接口实现
     * @return 接口实例
     */
    S getService();

    /**
     * 通用删除接口
     * @param id 主键
     * @return 结果
     */
    @PostMapping("/remove/{id}")
    @ApiOperation(value = "删除指定ID的对象")
    @SysLogs("删除指定ID的对象")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token",required = true)
    default ResponseResult remove(@PathVariable("id") UID id){
        getService().remove(id);
        return ResponseResult.e(ResponseCode.OK);
    }
}
