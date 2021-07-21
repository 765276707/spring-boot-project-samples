package com.aurora.logger.core.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

    /**
     * 获取 HttpServletRequest
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        return attributes!=null ? attributes.getRequest() : null;
    }

}
