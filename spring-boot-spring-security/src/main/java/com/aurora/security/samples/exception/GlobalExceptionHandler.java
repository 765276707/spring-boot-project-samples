package com.aurora.security.samples.exception;

import com.aurora.security.core.response.ErrorResponse;
import com.aurora.security.core.response.ErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * @author xzbcode
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 10000)
public class GlobalExceptionHandler {

    /**
     * 业务异常响应
     * @param e
     * @return
     */
    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException e) {
        // 响应
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ErrorResponse(ErrorStatus.BAD_REQUEST, e.getMessage()));

    }


}
