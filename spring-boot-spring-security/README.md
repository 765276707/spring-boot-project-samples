## 工程简介
SpringBoot工程整合SpringSecurity案例

### 支持的特性
- 基于Token认证
- 基于Redis鉴权
- 基础限流（令牌桶、缓存两种模式，可自定义实现）
- 防重放攻击（可自定义实现）
- 防盗链攻击（可自定义实现）
- 防XSS攻击

### 认证和鉴权
- 本案例是引入JWT作为Token，具有较好的通用性，认证示范代码:
~~~java
/**
 * 用户登录
 * @param accountLoginDTO 账号密码登录参数
 * @return
 */
@PostMapping("/login")
public ResultResponse<Token> login(@RequestBody @Validated AccountLoginDTO accountLoginDTO) {
    // 查询并验证用户
    User user = baseAuthService.executeLogin(accountLoginDTO);
    // 创建令牌
    Token token = jwtManager.createToken(user);
    // 保存登录用户信息
    userDetailsStorage.storeUserDetails(user);
    // 响应结果
    return ResultResponse.success("登录成功", token);
}
~~~

- 鉴权是与SpringSecurity的使用方式一致
~~~java
/**
 * 测试权限
 * 已拥有 perm:select
 * @return
 */
@PreAuthorize("hasAuthority('perm:select')")
@PostMapping("/testSelectPermission")
public ResultResponse testSelectPermission() {
    // 响应结果
    return ResultResponse.success("成功访问了 testSelectPermission");
}
~~~

- 认证的可配置项(支持在`application.yml`文件中进行配置):
~~~java
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
~~~


### 基础限流
提供了两种基础的限流方式，令牌桶和缓存，如果开启缓存支持需要有Redis环境
- 可配置项(支持在`application.yml`文件中进行配置)
~~~java
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
~~~

- 默认是使用的令牌桶方式，若要开启其他模式，只需要在项目中注入自己的 `IRateLimitProcessor` 的实现类即可

- 提供了基于Redis缓存的 `CacheRateLimitProcessor`, 开启方式同上
~~~java
@Bean
public IRateLimitProcessor rateLimitProcessor() {
    return new CacheRateLimitProcessor();
}
~~~


### 防重放攻击
- 请求头中需要提供 `Date` 和 `X-Track-Id` 两个值，这两个Key名称也支持自定义
    - `Date`: 请求时间戳（毫秒）
    - `X-Track-Id`: 全局唯一的请求追踪Id，如UUID等

- 默认提供的是基于Redis实现的 `CacheReplayAttackProcessor`，需要Redis环境
    
- 如果需要自己实现防重放的处理逻辑，只需要在项目中注入一个
`IReplayAttackProcessor` 的实现类即可

- 可配置项(支持在`application.yml`文件中进行配置)
~~~java
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
~~~


### 防盗链攻击
- 请求头中需要提供 `Referer` 值
    - `Date`: 请求时间戳（毫秒）
    - `X-Track-Id`: 全局唯一的请求追踪Id，如UUID等
    
- 如果需要自己实现黑名单和白名单获取方式，只需要在项目中注入一个
`IStealLinkListProvider` 的实现类即可

- 默认实现的 `PropertiesStealLinkListProvider`，就是直接在 `application.yml`文件中配置
~~~yaml
application:
  security:
    stealLink:
      blackList: -http://127.0.0.2:9000
~~~

- 可配置项(支持在`application.yml`文件中进行配置)
~~~java
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
~~~

### 防XSS攻击
- 防XSS攻击直接包装了 `HttpServletRequestWrapper` ，对请求内容进行XSS字符过滤

- 暂不支持自定义

- 可配置项(支持在`application.yml`文件中进行配置)
~~~java
public class XssAttackProperties {
        // 是否启用
        private Boolean enabled = true;
    }
~~~

### 常用配置示例
~~~yaml
application:
  security:
    token:
      # 令牌请求头
      header: Authorization
      # 令牌签发对象类型
      audience: app_name
      # 访问令牌过期时间（单位：秒，默认30分钟）
      accessTokenExpire: 300 * 60
      # 刷新令牌的过期时间（单位：秒，默认30天）
      refreshTokenExpire: 60 * 60 * 24 * 30
      # 令牌RSA私钥
      rsaPrivateKey: MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEA6K" +
        "7kQd+rTtlDfpFcFw9BBempm69CIBf1OarIB8StmDHaKk4T9Vt16PA1SjzYusIVWf5A3LQZn9qGwfT27jCU0QID" +
        "AQABAkA0pw7D0UdEiml5hI8gMLV4Dw3wStJdzM4TBJBLIbhejcrkS4GJ2Ej0xls+AUGWmA" +
        "CRB/jmXTv/DO5Gdk3DzrZ5AiEA+MKIlj1XQAtYH5F83XMrZSEo7vnSgo/zbpY7NpBWMF8CIQDvdJK+Jy" +
        "d7EbQeWLp28wt/53vm/gPmvXEirUB/QFOIzwIhAMNoRNUIAHbNsn6x0Y0/gBIj1zzKP+PR4l63YnI9NYc" +
        "vAiEApPHQA1xFPek8AYttJnLVAQ4bs0pWtaLZQ+HEA+PzptECIBfxaSFBVBUuRYm53lLNQAU8XCTXZenvbxSq4KTDZcpY
      # 令牌RSA公钥
      rsaPublicKey: MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAOiu5EHfq07ZQ36RXBcPQQXpqZuvQiAX9" +
        "TmqyAfErZgx2ipOE/VbdejwNUo82LrCFVn+QNy0GZ/ahsH09u4wlNECAwEAAQ==

    rateLimit:
      # 是否开启限流
      enabled: false
      # 令牌桶的频率（令牌桶模式有效）
      tokenBucketRate: 1
      # 刷新时间区间（缓存模式有效）
      interval: 5 * 60L
      # ip频率（缓存模式有效）
      ipRate: 5
      # 凭证频率（缓存模式有效）
      principalRate: 5

    replayAttack:
      # 是否开启防重放攻击
      enabled: false
      # 过期时间（秒）
      expire: 5 * 60L

    stealLink:
      # 是否开启防盗链攻击
      enabled: false
      # 选择白名单或者黑名单
      listType: BLACK_LIST
      # 黑名单列表
      blackList: -http://127.0.0.1:9000

    xssAttack:
      # 是否开启防XSS攻击
      enabled: false
~~~

