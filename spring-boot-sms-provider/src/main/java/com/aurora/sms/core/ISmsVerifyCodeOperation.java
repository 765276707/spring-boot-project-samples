package com.aurora.sms.core;

/**
 * 短信验证码业务
 * @author xzbcode
 */
public interface ISmsVerifyCodeOperation {

    /**
     * 发送短信验证码
     * @param phoneNumber 手机号
     * @param smsCodeTemplate 短信验证码模板
     * @param smsSignName 短信签名
     */
    void sendVerifyCode(String phoneNumber, String smsCodeTemplate, String smsSignName);

    /**
     * 校验短信验证码是否有效
     * @param phoneNumber 手机号
     * @param smsCodeTemplate 短信验证码模板
     * @param smsCode 收到的短信验证码
     * @return boolean
     */
    boolean checkVerifyCode(String phoneNumber, String smsCodeTemplate, String smsCode);

}
