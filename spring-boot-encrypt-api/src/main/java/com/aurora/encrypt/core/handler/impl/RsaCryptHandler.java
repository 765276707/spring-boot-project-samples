package com.aurora.encrypt.core.handler.impl;

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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

/**
 * RSA加解密模式
 * @author xzbcode
 */
@Slf4j
@Component("rsaCryptHandler")
public class RsaCryptHandler implements ICryptHandler {

    @Autowired
    private CryptoProperties cryptProp;

    @Override
    public AlgorithmType getAlgorithmType() {
        return AlgorithmType.RSA;
    }

    @Override
    public String encrypt(HttpHeaders headers, String plainText) throws EncryptBodyException {
        try {
            return RsaUtil.privateEncrypt(plainText, this.getPrivateKey());
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
                | UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException
                | InvalidKeySpecException e) {
            throw new EncryptBodyException(String.format("an error has occurred when plain text encrypted with rsa algorithm." +
                    " cause by : %s", e.getMessage()));
        }
    }

    @Override
    public String decrypt(HttpHeaders headers, String cipherText) throws DecryptBodyException {
        try {
            return RsaUtil.publicDecrypt(cipherText, this.getPublicKey());
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
                | UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException
                | InvalidKeySpecException e) {
            throw new EncryptBodyException(String.format("an error has occurred when cipher text decrypted with rsa algorithm." +
                    " cause by : %s", e.getMessage()));
        }
    }

    /**
     * <h2>获取令牌公钥</h2>
     * @return
     */
    private PublicKey getPublicKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        return RsaUtil.string2PublicKey(cryptProp.getRsa().getPublicKey());
    }

    /**
     * <h2>获取令牌私钥</h2>
     * @return
     */
    private PrivateKey getPrivateKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        return RsaUtil.string2PrivateKey(cryptProp.getRsa().getPrivateKey());
    }
}
