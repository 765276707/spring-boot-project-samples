package com.aurora.sms.provider.aliyun.support;

import org.springframework.stereotype.Component;

/**
 * 短信服务缓存支持
 * @author xzbcode
 */
@Component
public class SmsCacheProvider {

    /**
     * 短信验证码存入缓存
     * @param phoneNumber 手机号
     * @param smsCodeTemplate 模板
     * @param code 验证码
     */
    public void putCodeInCache(String phoneNumber, String smsCodeTemplate, String code) {

    }

    /**
     * 从缓存中获取短信验证码
     * @param phoneNumber 手机号
     * @param smsCodeTemplate 模板
     * @return
     */
    public String getCodeFromCache(String phoneNumber, String smsCodeTemplate) {
        return "123456";
    }

}
