package com.aurora.validation.core.contraint.annotation;

import com.aurora.validation.core.contraint.validator.IsEmailAddressValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 是否为合法的邮箱地址
 * @author xzbcode
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsEmailAddressValidator.class})
public @interface IsEmailAddress {

    // 提示信息
    String message() default "不合法的邮箱地址";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
