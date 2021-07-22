package com.aurora.oss.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;

@Configuration
public class AliyunOssConfig {

    @Resource
    AliyunOssProperties aliyunOssProperties;

    @Bean
    public OSS aliyunOssClient() {
        return new OSSClientBuilder()
                .build( aliyunOssProperties.getEndpoint(),
                        aliyunOssProperties.getAccessKey(),
                        aliyunOssProperties.getSecretKey());
    }

}
