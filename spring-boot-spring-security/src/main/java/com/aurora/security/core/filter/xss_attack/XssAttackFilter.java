package com.aurora.security.core.filter.xss_attack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * XSS过滤器
 * @author xzbcode
 */
@Slf4j
@SuppressWarnings("all")
public class XssAttackFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        // 传递重新包装后的Request
        chain.doFilter(new XssHttpServletRequestWrapper(request), response);
    }

}
