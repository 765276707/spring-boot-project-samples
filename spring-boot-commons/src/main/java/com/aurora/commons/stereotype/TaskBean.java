package com.aurora.commons.stereotype;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * <h1>任务类标识注解</h1>
 * @author xzb
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface TaskBean {

    @AliasFor(annotation = Component.class)
    String value() default "";

}
