package com.aurora.security.core.filter.limiter;


/**
 * 限流异常
 * @author xzbcode
 */
public class RateLimitException extends SecurityException {
    public RateLimitException() {
    }

    public RateLimitException(String message) {
        super(message);
    }

    public RateLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public RateLimitException(Throwable cause) {
        super(cause);
    }

}
