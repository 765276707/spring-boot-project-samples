package com.aurora.dict.core.annotation;

import com.aurora.dict.core.config.DictSupportAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 开启字典注解支持
 * @author xzbcode
 */
@Target({TYPE})
@Retention(RUNTIME)
@Documented
@Import({DictSupportAutoConfiguration.class})
public @interface EnableAopDictSupport {
}
