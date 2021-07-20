package com.aurora.validation.core.contraint.annotation;

import com.aurora.validation.core.contraint.validator.IsWordNumberUnderlineValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 是否为数字字母下划线
 * @author xzbcode
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsWordNumberUnderlineValidator.class})
public @interface IsWordNumberUnderline {

    // 提示信息
    String message() default "只能为字母、数字或下划线";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
