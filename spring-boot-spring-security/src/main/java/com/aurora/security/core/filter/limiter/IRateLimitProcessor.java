package com.aurora.security.core.filter.limiter;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 限流接口
 * @author xzbcode
 */
public interface IRateLimitProcessor {

    /** 限流 */
    boolean process(Authentication authentication, HttpServletRequest request);

}
