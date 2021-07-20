package com.aurora.excel.samples.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 静态资源文件的配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Excel文件
        // 注意：classpath:/temp 不需要再额外加resources，错误示例：classpath:/resources/temp
        registry.addResourceHandler("/importResult/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/temp/");
    }

}
