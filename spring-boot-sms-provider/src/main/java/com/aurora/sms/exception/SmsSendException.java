package com.aurora.sms.exception;

/**
 * 短信发送异常
 * @author xzbcode
 */
public class SmsSendException extends RuntimeException {

    public SmsSendException() {
        super();
    }

    public SmsSendException(String message) {
        super(message);
    }

    public SmsSendException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmsSendException(Throwable cause) {
        super(cause);
    }

    protected SmsSendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
