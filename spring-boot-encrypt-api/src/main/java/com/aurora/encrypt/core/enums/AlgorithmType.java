package com.aurora.encrypt.core.enums;

/**
 * 加解密算法类型
 * @author xzbcode
 */
public enum AlgorithmType {

    RSA("RSA"),
    AES("AES"),
    MIX("RSA_AES");

    private String value;

    AlgorithmType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
