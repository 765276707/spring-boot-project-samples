package com.aurora.commons.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <h1>错误响应</h1>
 * @author xzb
 */
@Data
@NoArgsConstructor
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
