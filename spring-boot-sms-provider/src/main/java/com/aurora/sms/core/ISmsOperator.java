package com.aurora.sms.core;

/**
 * 统一的短信操作入口
 * @author xzbcode
 */
public interface ISmsOperator {

    // 短信验证码操作
    ISmsVerifyCodeOperation opsForVerifyCode();

    // 短信通知操作
    ISmsNoticeOperation opsForNotice();

}
