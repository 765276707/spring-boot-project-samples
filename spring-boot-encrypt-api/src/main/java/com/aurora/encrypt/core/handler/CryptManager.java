package com.aurora.encrypt.core.handler;

import com.aurora.encrypt.core.enums.AlgorithmType;
import com.aurora.encrypt.core.exception.extd.DecryptBodyException;
import com.aurora.encrypt.core.exception.extd.EncryptBodyException;
import com.aurora.encrypt.core.properties.CryptoProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 加解密管理器
 * @author xzbcode
 */
@Slf4j
@Component
public class CryptManager implements BeanPostProcessor {

    // 加解密处理器索引
    private static final Map<AlgorithmType, ICryptHandler> cryptTypeIndex = new HashMap<>(AlgorithmType.values().length);

    @Resource
    private CryptoProperties cryptoProperties;

    @PostConstruct
    public void init() {
        this.getType();
    }

    /**
     * <h2>加密</h2>
     * @param headers 请求头
     * @param plainText 明文
     * @return String
     * @throws EncryptBodyException 加密失败异常
     */
    public String encrypt(HttpHeaders headers, String plainText) throws EncryptBodyException {
        return this.getHandlerByType(this.getType()).encrypt(headers, plainText);
    }

    /**
     * <h2>解密</h2>
     * @param headers 请求头
     * @param cipherText 密文
     * @return String
     * @throws DecryptBodyException 解密失败异常
     */
    public String decrypt(HttpHeaders headers, String cipherText) throws DecryptBodyException {
        return this.getHandlerByType(this.getType()).decrypt(headers, cipherText);
    }

    /**
     * <h2>获取配置的加解密器类型</h2>
     * @return
     */
    private AlgorithmType getType() {
        AlgorithmType type = cryptoProperties.getType();
        if (type == null) {
            type = AlgorithmType.AES;
        }
        log.info(" -= (^-^) =- the encryption and decryption strategy currently used by the application is {}", type.getValue());
        return type;
    }


    /**
     * <h2>获取对应的处理器</h2>
     * @param type 处理器类型
     * @return
     */
    private ICryptHandler getHandlerByType(AlgorithmType type) {
        ICryptHandler cryptHandler = cryptTypeIndex.get(type);
        if (cryptHandler == null) {
            throw new IllegalStateException("crypt handler not yet registered.");
        }
        return cryptHandler;
    }

    /**
     * 注册加解密器
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 不是加解密器直接返回
        if (!(bean instanceof ICryptHandler)) {
            return bean;
        }
        // 获取加解密器
        ICryptHandler handler = (ICryptHandler) bean;
        AlgorithmType type = handler.getAlgorithmType();
        // 重复注册
        if (cryptTypeIndex.containsKey(type)) {
            throw new IllegalStateException("repeat register crypt handler in manager.");
        }
        // 注册
        cryptTypeIndex.put(type, handler);
        return null;
    }

}
