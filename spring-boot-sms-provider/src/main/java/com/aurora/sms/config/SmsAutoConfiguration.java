package com.aurora.sms.config;

import com.aurora.sms.core.ISmsOperator;
import com.aurora.sms.provider.aliyun.AliyunSmsOperator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 短信验证码自动装配
 * @author xzbcode
 */
@Configuration
public class SmsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean({ISmsOperator.class})
    public ISmsOperator smsOperator() {
        // 默认实现阿里云短信
        return new AliyunSmsOperator();
    }

}
