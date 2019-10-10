package com.theus.health.base.common.annotation;

import java.lang.annotation.*;

/**
 * @author tangwei
 * @date 2019-07-28 21:06
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLogs {
    String value();
}
