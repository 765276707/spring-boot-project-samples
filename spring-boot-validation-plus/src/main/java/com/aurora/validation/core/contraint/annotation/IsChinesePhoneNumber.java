package com.aurora.validation.core.contraint.annotation;

import com.aurora.validation.core.contraint.validator.IsChinesePhoneNumberValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 是否为合法的中国（大陆地区）电话号码
 * @author xzbcode
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsChinesePhoneNumberValidator.class})
public @interface IsChinesePhoneNumber {

    // 提示信息
    String message() default "不合法的手机号码";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
