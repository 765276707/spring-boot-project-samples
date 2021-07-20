package com.aurora.validation.core.contraint.annotation;

import com.aurora.validation.core.contraint.validator.IsMacAddressValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 是否为合法的Mac地址
 * @author xzbcode
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsMacAddressValidator.class})
public @interface IsMacAddress {

    // 提示信息
    String message() default "不合法的MAC地址";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
