package com.aurora.dict.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 字典解析属性
 * @author xzbcode
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Inherited
public @interface DictField {

    // 对应Code所在字段
    String keyFieldName();

    // 对应字典Code
    String code();

    // 未匹配到字典的默认展示值
    String defaultValue() default "";

}
