package com.theus.health.main.controller;

import com.theus.health.base.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tangwei
 * @date 2019/5/3 11:23
 */
@RestController
@RequestMapping("demo")
public class DemoController {

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
