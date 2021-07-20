package com.aurora.security.core.properties;

import com.aurora.security.core.model.ListType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

/**
 * 增加的过滤器可配置属性
 * @author xzbcode
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application.security")
@SuppressWarnings("all")
public class SecurityProperties {

    // 令牌配置
    private TokenProperties token = new TokenProperties();
    // 限流配置
    private RateLimitProperties rateLimit = new RateLimitProperties();
    // 防重放配置
    private ReplayAttackProperties replayAttack = new ReplayAttackProperties();
    // XSS配置
    private XssAttackProperties xssAttack = new XssAttackProperties();
    // 防盗链配置
    private StealLinkProperties stealLink = new StealLinkProperties();

    @Getter
    @Setter
    public class TokenProperties {
        // 令牌请求头
        private String header = "Authorization";
        // 令牌签发对象类型
        private String audience = "";
        // 令牌类型
        private String type = "Brazer";
        // 授权范围
        private String scope = "Application";
        // 访问令牌过期时间（单位：秒，默认30分钟）
        private Integer accessTokenExpire = 300 * 60;
        // 刷新令牌的过期时间（单位：秒，默认30天）
        private Integer refreshTokenExpire = 60 * 60 * 24 * 30;
        // 令牌RSA私钥
        private String rsaPrivateKey = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEA6K" +
                "7kQd+rTtlDfpFcFw9BBempm69CIBf1OarIB8StmDHaKk4T9Vt16PA1SjzYusIVWf5A3LQZn9qGwfT27jCU0QID" +
                "AQABAkA0pw7D0UdEiml5hI8gMLV4Dw3wStJdzM4TBJBLIbhejcrkS4GJ2Ej0xls+AUGWmA" +
                "CRB/jmXTv/DO5Gdk3DzrZ5AiEA+MKIlj1XQAtYH5F83XMrZSEo7vnSgo/zbpY7NpBWMF8CIQDvdJK+Jy" +
                "d7EbQeWLp28wt/53vm/gPmvXEirUB/QFOIzwIhAMNoRNUIAHbNsn6x0Y0/gBIj1zzKP+PR4l63YnI9NYc" +
                "vAiEApPHQA1xFPek8AYttJnLVAQ4bs0pWtaLZQ+HEA+PzptECIBfxaSFBVBUuRYm53lLNQAU8XCTXZenvbxSq4KTDZcpY";
        // 令牌RSA公钥
        private String rsaPublicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAOiu5EHfq07ZQ36RXBcPQQXpqZuvQiAX9" +
                "TmqyAfErZgx2ipOE/VbdejwNUo82LrCFVn+QNy0GZ/ahsH09u4wlNECAwEAAQ==";
    }

    @Getter
    @Setter
    public class RateLimitProperties {
        // 是否配置该过滤器
        private Boolean enabled = false;
        // 令牌桶的频率（令牌桶模式有效）
        private Integer tokenBucketRate = 1;
        // 时间区间（缓存模式有效）
        private Long interval = 5 * 60L;
        // ip频率（缓存模式有效）
        private Integer ipRate = 5;
        // 凭证频率（缓存模式有效）
        private Integer principalRate = 5;

    }

    @Getter
    @Setter
    public class ReplayAttackProperties {
        // 是否启用
        private Boolean enabled = false;
        // 请求时间
        private String timestampFlag = "Date";
        // 请求标识
        private String trackFlag = "X-Track-Id";
        // 过期时间（秒）
        private Long expire = 5 * 60L;

    }

    @Getter
    @Setter
    public class XssAttackProperties {
        // 是否启用
        private Boolean enabled = true;
    }

    @Getter
    @Setter
    public class StealLinkProperties {
        // 是否启用
        private Boolean enabled = false;
        // 名单类型
        private ListType listType = ListType.WHITE_LIST;
        // 白名单
        private Set<String> whiteList = Collections.emptySet();
        // 黑名单
        private Set<String> blackList = Collections.emptySet();
    }
}
