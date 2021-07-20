package com.aurora.security.core.model;

/**
 * 令牌类型
 * @author xzbcode
 */
public enum TokenType {

    ACCESS_TOKEN("access"),
    REFRESH_TOKEN("refresh");

    private String value;

    TokenType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
