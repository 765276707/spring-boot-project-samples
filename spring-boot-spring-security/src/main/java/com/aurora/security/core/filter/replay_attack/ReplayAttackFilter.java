package com.aurora.security.core.filter.replay_attack;

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
 * 防重放攻击过滤器
 * @author xzbcode
 */
@Slf4j
@SuppressWarnings("all")
public class ReplayAttackFilter extends OncePerRequestFilter {

    private final IReplayAttackProcessor replayAttackProcessor;

    public ReplayAttackFilter(IReplayAttackProcessor replayAttackProcessor) {
        this.replayAttackProcessor = replayAttackProcessor;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            if (!this.replayAttackProcessor.process(request)) {
                this.handleError(response);
                return;
            }
        } catch (ReplayAttackException e) {
            if (log.isWarnEnabled()) {
                // 输出警告日志
                log.warn("{} cause by: {}", e.getMessage(), e);
            }
            this.handleError(response);
            return;
        }

        // 放行
        chain.doFilter(request, response);
    }


    private void handleError(HttpServletResponse response) throws IOException {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ResponseUtil.writeJSON(response, badRequest,
                new ErrorResponse(ErrorStatus.BAD_REQUEST));
    }

}
