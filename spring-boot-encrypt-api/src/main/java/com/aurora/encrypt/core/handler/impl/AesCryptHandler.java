package com.aurora.encrypt.core.handler.impl;

import com.aurora.encrypt.core.cipher.AesUtil;
import com.aurora.encrypt.core.enums.AlgorithmType;
import com.aurora.encrypt.core.exception.extd.DecryptBodyException;
import com.aurora.encrypt.core.exception.extd.EncryptBodyException;
import com.aurora.encrypt.core.handler.ICryptHandler;
import com.aurora.encrypt.core.properties.CryptoProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * AES加解密模式
 * @author xzbcode
 */
@Slf4j
@Component("aesCryptHandler")
public class AesCryptHandler implements ICryptHandler {

    @Autowired
    private CryptoProperties cryptProp;

    @Override
    public AlgorithmType getAlgorithmType() {
        return AlgorithmType.AES;
    }

    @Override
    public String encrypt(HttpHeaders headers, String plainText) throws EncryptBodyException {
        try {
            return AesUtil.encrypt(plainText,
                    cryptProp.getAes().getKey(), cryptProp.getAes().getIv());
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException
                | InvalidKeyException | UnsupportedEncodingException  | BadPaddingException
                | IllegalBlockSizeException e) {
            throw new EncryptBodyException(String.format("an error has occurred when plain text encrypted with aes algorithm." +
                    " cause by : %s", e.getMessage()));
        }
    }

    @Override
    public String decrypt(HttpHeaders headers, String cipherText) throws DecryptBodyException {
        try {
            return AesUtil.decrypt(cipherText,
                    cryptProp.getAes().getKey(), cryptProp.getAes().getIv());
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException
                | InvalidKeyException | UnsupportedEncodingException  | BadPaddingException
                | IllegalBlockSizeException e) {
            throw new EncryptBodyException(String.format("an error has occurred when cipher text decrypted with aes algorithm." +
                    " cause by : %s", e.getMessage()));
        }
    }

}
