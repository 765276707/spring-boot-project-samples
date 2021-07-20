package com.aurora.security.core.filter.authc;

import org.springframework.security.core.AuthenticationException;

/**
 * JWT 令牌异常
 * @author xzbcode
 */
public class JwtSecurityException extends AuthenticationException {

    public JwtSecurityException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtSecurityException(String msg) {
        super(msg);
    }

}
