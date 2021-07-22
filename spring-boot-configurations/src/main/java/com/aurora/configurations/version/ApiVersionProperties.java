package com.aurora.configurations.version;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(
        prefix = "application.api-version"
)
public class ApiVersionProperties {

    // 全局统一接口版本号
    private String[] version;

    public String[] getVersion() {
        return version;
    }

    public void setVersion(String[] version) {
        this.version = version;
    }
}
