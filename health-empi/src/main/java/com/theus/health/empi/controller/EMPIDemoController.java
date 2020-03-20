package com.theus.health.empi.controller;

import com.theus.health.base.service.DemoService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tangwei
 * @date 2020-03-20 9:04
 */
@RestController
@RequestMapping("EMPIDemo")
@Api(tags = {"EMPI测试"})
public class EMPIDemoController {

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
        // test
        return "hello world";
    }
}
