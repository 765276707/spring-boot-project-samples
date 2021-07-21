package com.aurora.logger.core.annotation;

import com.aurora.logger.core.service.OperateType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志注解
 * @author xzbcode
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AopLogger {

    // 操作描述
    String desc() default "";

    // 操作类型， 默认为其它
    OperateType type() default OperateType.DEFAULT;

}
