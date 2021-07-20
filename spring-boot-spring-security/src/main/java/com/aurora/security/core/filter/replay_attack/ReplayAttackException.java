package com.aurora.security.core.filter.replay_attack;

/**
 * 防重放异常
 * @author xzbcode
 */
public class ReplayAttackException extends SecurityException {

    public ReplayAttackException() {
    }

    public ReplayAttackException(String message) {
        super(message);
    }

    public ReplayAttackException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReplayAttackException(Throwable cause) {
        super(cause);
    }

}
