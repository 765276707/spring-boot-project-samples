package com.aurora.security.core.model;

/**
 * 防盗链名单类型
 * @author xzbcode
 */
public enum ListType {

    WHITE_LIST("白名单"),
    BLACK_LIST("黑名单");

    private String value;

    ListType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
