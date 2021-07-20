package com.aurora.validation.core.contraint.annotation;

import com.aurora.validation.core.contraint.validator.IsAssignableFromValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证是否为目标类的可转换的类
 * @author xzbcode
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsAssignableFromValidator.class})
public @interface IsAssignableFrom {

    // 目标类
    Class<?> targetClazz() default Object.class;

    // 提示信息
    String message() default "无效的目标类";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
