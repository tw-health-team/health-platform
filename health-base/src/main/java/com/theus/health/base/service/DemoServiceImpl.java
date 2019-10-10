package com.theus.health.base.service;

import org.springframework.stereotype.Service;

/**
 * @author tangwei
 * @date 2019/5/3 14:00
 */
@Service
public class DemoServiceImpl implements DemoService {


    @Override
    public String test() {
        return "interface test";
    }
}
