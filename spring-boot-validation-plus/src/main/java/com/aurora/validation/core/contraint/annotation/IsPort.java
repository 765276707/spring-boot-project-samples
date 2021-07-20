package com.aurora.validation.core.contraint.annotation;

import com.aurora.validation.core.contraint.validator.IsPortValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 是否为合法的端口号，0-65535
 * @author xzbcode
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsPortValidator.class})
public @interface IsPort {

    // 提示信息
    String message() default "不合法的端口号";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
