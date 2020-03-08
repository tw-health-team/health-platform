package com.theus.health.booking.controller;

import com.theus.health.base.service.DemoService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tangwei
 * @date 2020-03-08 20:06
 */
@RestController
@RequestMapping("bookDemo")
@Api(tags = {"预约测试"})
public class BookDemoController {

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
