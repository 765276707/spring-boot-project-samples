package com.aurora.validation.core.contraint.annotation;

import com.aurora.validation.core.contraint.validator.IsEqualFieldValueValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 验证某两个字段的值是否一致
 * @author xzbcode
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsEqualFieldValueValidator.class})
public @interface IsEqualFieldValue {

    // 目标字段
    String targetField();
    // 来源字段
    String sourceField();

    // 提示信息
    String message() default "两个字段的值不一致";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
