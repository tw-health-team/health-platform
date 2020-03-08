package com.theus.health.elderlycare.controller;

import com.theus.health.base.service.DemoService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tangwei
 * @date 2020-03-08 20:55
 */
@RestController
@RequestMapping("elderlyDemo")
@Api(tags = {"养老测试"})
public class ElderlyDemoController {

    @Resource
    DemoService demoService;

    @GetMapping("test")
    public String test() {
        //ces34
        //测试
        return demoService.test();
    }

    @GetMapping("test2")
    public String test2() {
        return "elderly care service";
    }
}
