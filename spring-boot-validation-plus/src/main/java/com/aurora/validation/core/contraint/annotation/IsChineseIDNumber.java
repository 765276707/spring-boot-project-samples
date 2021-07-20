package com.aurora.validation.core.contraint.annotation;

import com.aurora.validation.core.contraint.validator.IsChineseIDNumberValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 是否为中国身份证号码
 * @author xzbcode
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsChineseIDNumberValidator.class})
public @interface IsChineseIDNumber {

    // 提示信息
    String message() default "不合法的身份证号码";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
