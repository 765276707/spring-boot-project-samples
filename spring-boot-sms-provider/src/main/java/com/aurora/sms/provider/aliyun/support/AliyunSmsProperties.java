package com.aurora.sms.provider.aliyun.support;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * 阿里云短信配置
 * @author xzbcode
 */
@Getter
@Setter
@Validated
@Component
@PropertySource(value="classpath:config/sms-aliyun.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "sms.aliyun")
public class AliyunSmsProperties {

    // 验证码过期时间，默认5分钟，单位秒
    private Long codeExpire = 300L;

    // 地址源ID
    @NotBlank(message = "阿里云短信机房区域未配置： regionId")
    private String regionId;

    // 产品
    @NotBlank(message = "阿里云短信产品名未配置： product")
    private String product;

    // 域名
    @NotBlank(message = "阿里云短信域名未配置： endpoint")
    private String endpoint;

    // 访问密钥ID
    @NotBlank(message = "阿里云短信密钥未配置： accessKeyId")
    private String accessKeyId;

    // 访问密钥
    @NotBlank(message = "阿里云短信密钥未配置： accessKeySecret")
    private String accessKeySecret;

}
