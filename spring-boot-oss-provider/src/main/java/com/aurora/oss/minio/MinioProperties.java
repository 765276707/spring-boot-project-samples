package com.aurora.oss.minio;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@PropertySource(
        value="classpath:config/oss-minio.properties", encoding = "UTF-8"
)
@ConfigurationProperties(
        prefix = "oss.minio"
)
public class MinioProperties {

    private String endpoint;

    private String accessKey;

    private String secretKey;

}
