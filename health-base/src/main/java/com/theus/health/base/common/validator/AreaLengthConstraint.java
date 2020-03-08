package com.theus.health.base.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author tangwei
 * @date 2020-02-28 21:13
 * 说明：@Target用于指定使用范围；
 * 说明：@Retention(RetentionPolicy.RUNTIME)表示注解在运行时可以通过反射获取到；
 * 说明：@Constraint(validatedBy = xxx.class)指定该注解校验逻辑
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy=AreaLengthConstraintValidator.class)
public @interface AreaLengthConstraint {
    String message() default "行政区划编码位数必须是2、4、6、9或12位！";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
