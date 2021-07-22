package com.aurora.oss.aliyun;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@PropertySource(
        value="classpath:config/oss-aliyun.properties", encoding = "UTF-8"
)
@ConfigurationProperties(
        prefix = "oss.aliyun"
)
public class AliyunOssProperties {

    private String endpoint;

    private String accessKey;

    private String secretKey;

}
