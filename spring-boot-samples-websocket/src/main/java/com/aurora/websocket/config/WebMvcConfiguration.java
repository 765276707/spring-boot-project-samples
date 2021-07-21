package com.aurora.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * 静态资源文件的配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 注意：classpath:/static 不需要再额外加resources，错误示例：classpath:/resources/static
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
    }

    /**
     * 默认开启跨域支持
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600)
                .allowedHeaders("*");
    }

}
