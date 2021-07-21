package com.aurora.dict.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标识实体类含有需要字典解析的字段
 * @author xzbcode
 */
@Target({TYPE})
@Retention(RUNTIME)
@Documented
@Inherited
public @interface DictEntity {

    // 是否解析属性中的对象、集合中的对象、数组中对象
    boolean analysisProperty() default true;

}
