package com.aurora.commons.stereotype;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * <h1>Spring拦截器标识注解</h1>
 * @author xzb
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Interceptor {

    @AliasFor(annotation = Component.class)
    String value() default "";

}
