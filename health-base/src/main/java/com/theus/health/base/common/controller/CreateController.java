package com.theus.health.base.common.controller;

import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.common.service.CreateService;
import com.theus.health.core.bean.ResponseCode;
import com.theus.health.core.bean.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author tangwei
 * @date 2019-08-17 11:58
 */
public interface CreateController <AD,S extends CreateService<AD>> {

    /**
     * 获取服务接口，子接口实现
     * @return 接口实例
     */
    S getService();

    /**
     * 通用添加接口
     * @param a 实体
     * @return 结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加新增")
    @SysLogs("添加新增")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token", required = true)
    default ResponseResult add(@RequestBody @Validated AD a) {
        getService().add(a);
        return ResponseResult.e(ResponseCode.OK);
    }
}
