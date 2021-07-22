package com.aurora.configurations.error_advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 基础异常处理
 * @author xzbcode
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 10000)
public class BasicExceptionAdvice {


    /**
     * 参数错误
     * @HttpStatus 400
     * @param e
     * @return
     */
    @ExceptionHandler(value={
            ValidationException.class,
            BindException.class,
            MissingServletRequestParameterException.class,
            MissingPathVariableException.class,
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<String> handlerParametersException(Exception e) {
        e.printStackTrace();
        // 获取错误消息
        String errorMessage = "非法参数，请求已被拒绝";
        if (e instanceof ValidationException
                || e instanceof MissingServletRequestParameterException
                || e instanceof MissingPathVariableException) {
            errorMessage = e.getMessage();
        }
        else if (e instanceof MethodArgumentNotValidException) {
            errorMessage = getParameterErrorMessage((MethodArgumentNotValidException) e, errorMessage);
        }
        else if (e instanceof BindException) {
            errorMessage = getParameterErrorMessage((BindException) e, errorMessage);
        }
        else if (e instanceof HttpMessageNotReadableException
                || e instanceof MethodArgumentTypeMismatchException) {
            errorMessage = "参数类型或格式错误";
        }
        // 响应
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }


    /**
     * 获取参数错误信息
     * @param e
     * @param defaultMessage
     * @return
     */
    private String getParameterErrorMessage(ConstraintViolationException e, String defaultMessage) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Optional<ConstraintViolation<?>> firstConstraintViolation = constraintViolations.stream().findFirst();
        if (firstConstraintViolation.isPresent()) {
            return firstConstraintViolation.get().getMessage();
        }
        return defaultMessage;
    }

    /**
     * 获取参数错误信息
     * @param e
     * @param defaultMessage
     * @return
     */
    private String getParameterErrorMessage(MethodArgumentNotValidException e, String defaultMessage) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        if (!CollectionUtils.isEmpty(fieldErrors)) {
            return fieldErrors.get(0).getDefaultMessage();
        }
        return defaultMessage;
    }

    /**
     * 获取参数错误信息
     * @param e
     * @param defaultMessage
     * @return
     */
    private String getParameterErrorMessage(BindException e, String defaultMessage) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        if (!CollectionUtils.isEmpty(fieldErrors)) {
            return fieldErrors.get(0).getDefaultMessage();
        }
        return defaultMessage;
    }
}
