package com.aurora.security.core.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ErrorResponse {

    // 错误码
    private Integer errorCode;
    // 错误消息
    private String errorMessage;

    public ErrorResponse(ErrorStatus status) {
        this.errorCode = status.getCode();
        this.errorMessage = status.getMessage();
    }

    public ErrorResponse(ErrorStatus status, String errorMessage) {
        this.errorCode = status.getCode();
        this.errorMessage = errorMessage;
    }

}
