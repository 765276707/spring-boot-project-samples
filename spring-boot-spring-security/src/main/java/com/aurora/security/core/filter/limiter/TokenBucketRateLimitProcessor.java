package com.aurora.security.core.filter.limiter;

import com.aurora.security.core.properties.SecurityProperties;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * 基于令牌桶的简单实现
 * @author xzbcode
 */
@Slf4j
@SuppressWarnings("all")
public class TokenBucketRateLimitProcessor implements IRateLimitProcessor{

    @Autowired
    private SecurityProperties securityProperties;

    // 限流工具类
    private static RateLimiter rateLimiter;

    @PostConstruct
    public void init() {
        rateLimiter = RateLimiter.create(securityProperties.getRateLimit().getTokenBucketRate());
    }

    @Override
    public boolean process(Authentication authentication, HttpServletRequest request) {
        return rateLimiter.tryAcquire();
    }
}
