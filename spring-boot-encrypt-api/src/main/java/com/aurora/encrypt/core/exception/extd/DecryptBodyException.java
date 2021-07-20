package com.aurora.encrypt.core.exception.extd;

import com.aurora.encrypt.core.exception.ApiCryptException;

/**
 * 请求Body参数解密异常
 * @author xzbcode
 */
public class DecryptBodyException extends ApiCryptException {

    public DecryptBodyException(String message) {
        super(message);
    }

}
