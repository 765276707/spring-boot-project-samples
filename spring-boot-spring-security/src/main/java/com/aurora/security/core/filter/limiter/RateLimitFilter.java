package com.aurora.security.core.filter.limiter;

import com.aurora.security.core.response.ErrorResponse;
import com.aurora.security.core.response.ErrorStatus;
import com.aurora.security.core.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 限流过滤器
 * @author xzbcode
 */
@Slf4j
@SuppressWarnings("all")
public class RateLimitFilter extends OncePerRequestFilter {

    private final IRateLimitProcessor rateLimitProcessor;

    public RateLimitFilter(IRateLimitProcessor rateLimitProcessor) {
        this.rateLimitProcessor = rateLimitProcessor;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        boolean process = rateLimitProcessor.process(null, request);
        if (!process) {
            HttpStatus tooManyRequests = HttpStatus.TOO_MANY_REQUESTS;
            ResponseUtil.writeJSON(response, tooManyRequests,
                    new ErrorResponse(ErrorStatus.TOO_MANY_REQUESTS));
            return;
        }
        // 继续执行
        chain.doFilter(request, response);
    }
}
