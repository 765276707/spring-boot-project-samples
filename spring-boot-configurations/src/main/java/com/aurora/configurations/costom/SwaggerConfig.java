package com.aurora.configurations.costom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * Swagger2常用配置
 * @author xzbcode
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket group1Docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(getApiInfo())
                .groupName("测试分组1")
                .select().apis(RequestHandlerSelectors.basePackage("com.xzbcode.swagger.controller1"))
                .build();
    }


    private ApiInfo getApiInfo() {
        Contact contact = new Contact("xzbcode", "https://www.user.com", "xxxxxxxxx@qq.com");
        return new ApiInfo(
                "Api Documentation",
                "Api Documentation",
                "1.0", "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList(8));
    }

}
