package com.aurora.encrypt.core.handler;

import com.aurora.encrypt.core.enums.AlgorithmType;
import com.aurora.encrypt.core.exception.extd.DecryptBodyException;
import com.aurora.encrypt.core.exception.extd.EncryptBodyException;
import org.springframework.http.HttpHeaders;

/**
 * <h1>抽象加解密处理器</h1>
 * @author xzb
 */
public interface ICryptHandler {

    /** 加解密器类型 */
    AlgorithmType getAlgorithmType();

    /** 加密 */
    String encrypt(HttpHeaders headers, String plainText) throws EncryptBodyException;

    /** 解密 */
    String decrypt(HttpHeaders headers, String cipherText) throws DecryptBodyException;

}
