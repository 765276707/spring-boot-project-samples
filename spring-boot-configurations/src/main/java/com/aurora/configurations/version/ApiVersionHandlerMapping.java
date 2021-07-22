package com.aurora.configurations.version;//package com.admin.framework.web.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * 接口版本控制
 * @author xzbcode
 */
@Component
public class ApiVersionHandlerMapping extends RequestMappingHandlerMapping {

    @Autowired
    private ApiVersionProperties versionProperties;

    @Override
    protected boolean isHandler(Class<?> beanType) {
        return AnnotatedElementUtils.hasAnnotation(beanType, Controller.class);
    }

    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        // 获取配置文件的版本号
        String[] globalVersion = versionProperties.getVersion();
        // 获取类上的注解
        ApiVersion typeVersion = AnnotationUtils.findAnnotation(method.getDeclaringClass(), ApiVersion.class);
        // 获取方法上的注解
        ApiVersion methodVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        // 方法注解优先
        String[] version = null;
        if (methodVersion != null) {
            version = methodVersion.value();
        } else if (typeVersion != null) {
            version = typeVersion.value();
        } else if (globalVersion != null) {
            version = globalVersion;
        }
        // 解析请求路径
        String[] urlPatterns = version == null ? new String[0] : version;
        PatternsRequestCondition apiCondition = new PatternsRequestCondition(urlPatterns);
        PatternsRequestCondition oldCondition = mapping.getPatternsCondition();
        PatternsRequestCondition updatedCondition = apiCondition.combine(oldCondition);
        // 构建RequestMappingInfo
        mapping = new RequestMappingInfo(mapping.getName(), updatedCondition, mapping.getMethodsCondition(),
                mapping.getParamsCondition(), mapping.getHeadersCondition(), mapping.getConsumesCondition(),
                mapping.getProducesCondition(), mapping.getCustomCondition());
        super.registerHandlerMethod(handler, method, mapping);
    }

}
