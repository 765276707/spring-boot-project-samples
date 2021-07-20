package com.aurora.security.core.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 响应结果
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResultResponse<T> {

    private Integer code;
    private String message;
    private T data;

    private ResultResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 执行成功
     * @return
     */
    public static ResultResponse success(String message) {
        return new ResultResponse(200, message);
    }

    /**
     * 执行成功
     * @param data 数据
     * @param <T> 数据类型
     * @return
     */
    public static <T> ResultResponse success(String message, T data) {
        return new ResultResponse<T>(200, message, data);
    }

    /**
     * 执行失败
     * @return
     */
    public static ResultResponse failure(String message) {
        return new ResultResponse(100, message);
    }

}
