package com.aurora.security.core.endpoint;

import com.aurora.security.core.response.ErrorResponse;
import com.aurora.security.core.response.ErrorStatus;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 权限不足响应
 * @author xzbcode
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DefaultAccessDeniedAdvice {

    /**
     * 由于权限异常会被ExceptionHandler优先捕获，
     * 从而无法进入AccessDeniedHandler处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<ErrorResponse> handlerAccessDeniedException(AccessDeniedException e) {
       return ResponseEntity.status(HttpStatus.FORBIDDEN)
               .body(new ErrorResponse(ErrorStatus.FORBIDDEN));
    }

}
