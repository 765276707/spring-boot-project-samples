package com.aurora.security.core.response;

import org.springframework.http.HttpStatus;

public enum ErrorStatus {

    // 400，请求参数错误
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "请求参数错误"),
    // 401，身份认证失败
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "身份认证失败"),
    // 403，访问权限不足
    FORBIDDEN(HttpStatus.FORBIDDEN, "访问权限不足"),
    // 404，资源不存在
    NOT_FOUND(HttpStatus.NOT_FOUND, "资源不存在"),
    // 408，请求超时
    REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "请求超时"),
    // 429，请求次数过多
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "请求次数过多"),
    // 500，服务器内部错误
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "服务器内部错误");

    private Integer code;
    private String message;


    ErrorStatus(HttpStatus status, String message) {
        this.code = status.value();
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
