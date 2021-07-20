package com.aurora.encrypt.core.handler.impl;

import com.aurora.encrypt.core.cipher.AesUtil;
import com.aurora.encrypt.core.cipher.RsaUtil;
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
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.UUID;

/**
 * RSA AES混合加解密模式
 * @author xzbcode
 */
@Slf4j
@Component("mixCryptHandler")
public class RsaAeaCryptHandler implements ICryptHandler {

    @Autowired
    private CryptoProperties cryptProp;

    @Override
    public AlgorithmType getAlgorithmType() {
        return AlgorithmType.MIX;
    }

    @Override
    public String encrypt(HttpHeaders headers, String plainText) throws EncryptBodyException {
        // 随机生成一个AES的密钥
        String aesKey = this.randomGeneratorAesKey();
        try {
            // RSA加密密钥
            String cAesKey = RsaUtil.privateEncrypt(plainText, this.getPrivateKey());
            // 加入响应头
            headers.add(this.getAesKeyInHeader(), cAesKey);
            // AES加密响应数据
            return AesUtil.encrypt(plainText,
                    cryptProp.getAes().getKey(), cryptProp.getRsaAes().getAesIv());
        }  catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
                | UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException
                | InvalidKeySpecException | InvalidAlgorithmParameterException e) {
            throw new EncryptBodyException(String.format("an error has occurred when plain text encrypted with rsa mix aes algorithm." +
                    " cause by : %s", e.getMessage()));
        }
    }

    @Override
    public String decrypt(HttpHeaders headers, String cipherText) throws DecryptBodyException {
        // 获取AES的密钥
        String cAesKey = this.getAesKeyFromHeader(headers);
        try {
            // RSA解密密钥
            String aesKey = RsaUtil.privateDecrypt(cAesKey, this.getPrivateKey());
            // AES解密请求体内容
            return AesUtil.decrypt(cipherText, aesKey, cryptProp.getRsaAes().getAesIv());
        }  catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
                | UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException
                | InvalidKeySpecException | InvalidAlgorithmParameterException e) {
            throw new EncryptBodyException(String.format("an error has occurred when plain text encrypted with rsa mix aes algorithm." +
                    " cause by : %s", e.getMessage()));
        }
    }


    /**
     * <h1>获取Aes密钥</h1>
     * @param headers 请求头
     * @return
     */
    private String getAesKeyFromHeader(HttpHeaders headers) {
        List<String> aesKeys = headers.get(this.getAesKeyInHeader());
        if (aesKeys==null || aesKeys.isEmpty()) {
            throw new IllegalArgumentException("缺失必要的请求头");
        }
        return aesKeys.get(0);
    }

    /**
     * <h1>生成随机16位的AES密钥</h1>
     * @return
     */
    private String randomGeneratorAesKey() {
        return UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 15)
                .toUpperCase();
    }

    /**
     * <h2>获取令牌公钥</h2>
     * @return
     */
    private PublicKey getPublicKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        return RsaUtil.string2PublicKey(cryptProp.getRsaAes().getRsaPublicKey());
    }

    /**
     * <h2>获取令牌私钥</h2>
     * @return
     */
    private PrivateKey getPrivateKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        return RsaUtil.string2PrivateKey(cryptProp.getRsaAes().getRsaPrivateKey());
    }

    /**
     * <h2>获取在请求头的中AES密钥</h2>
     * @return
     */
    private String getAesKeyInHeader() {
        return cryptProp.getRsaAes().getAesHeaderKey();
    }
}
