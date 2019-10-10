package com.theus.health.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 打war包：1.入口类中继承 SpringBootServletInitializer 并实现 configure 方法
 *         2.pom.xml中packaging值改为war
 *
 * @author tangwei
 * @date 2019/5/3 11:18
 */
@SpringBootApplication(scanBasePackages = "com.theus.health")
@MapperScan("com.theus.health.*.mapper")
public class HealthMainApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(HealthMainApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(HealthMainApplication.class, args);
    }
}
