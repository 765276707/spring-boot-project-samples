package com.aurora.commons.domain.response;

public enum RespStatus {

    //业务执行成功
    SUCCESS(200, "success"),
    //业务执行失败
    FAILURE(100, "failure");

    private Integer code;
    private String message;

    RespStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
