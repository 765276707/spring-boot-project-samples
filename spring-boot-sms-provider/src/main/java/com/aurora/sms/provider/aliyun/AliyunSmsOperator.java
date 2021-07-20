package com.aurora.sms.provider.aliyun;

import com.aurora.sms.core.ISmsNoticeOperation;
import com.aurora.sms.core.ISmsOperator;
import com.aurora.sms.core.ISmsVerifyCodeOperation;
import com.aurora.sms.provider.aliyun.support.AliyunSmsProperties;
import com.aurora.sms.provider.aliyun.support.SmsCacheProvider;
import javax.annotation.Resource;

/**
 * 阿里云短信服务
 * @author xzbcode
 */
public class AliyunSmsOperator implements ISmsOperator {

    @Resource
    private AliyunSmsProperties aliyunSmsProperties;
    @Resource
    private SmsCacheProvider smsCacheProvider;

    @Override
    public ISmsVerifyCodeOperation opsForVerifyCode() {
        // 实现阿里云短信验证码服务
        return new AliyunSmsVerifyCodeOperation(aliyunSmsProperties, smsCacheProvider);
    }

    @Override
    public ISmsNoticeOperation opsForNotice() {
        // 实现阿里云短信通知服务
        return new AliyunSmsNoticeOperation(aliyunSmsProperties);
    }

}
