package com.theus.health.base.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.theus.health.base.common.annotation.SysLogs;
import com.theus.health.base.common.service.QueryService;
import com.theus.health.core.bean.ResponseCode;
import com.theus.health.core.bean.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author tangwei
 * @date 2019-08-17 12:04
 */
public interface QueryController<E,FD,S extends QueryService<E,FD>> {

    /**
     * 获取服务接口，子接口实现
     * @return 接口实例
     */
    S getService();

    /**
     * 通用查询接口
     * @param findDTO 查询条件
     * @return 结果
     */
    @PostMapping("/list")
    @SysLogs("分页获取所有列表")
    @ApiOperation(value = "分页获取所有列表")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token",required = true)
    default ResponseResult<IPage<E>> list(@RequestBody FD findDTO){
        return ResponseResult.e(ResponseCode.OK,getService().list(findDTO));
    }

}