## 工程简介

### 传输加密

如果请求处于SSL加密环境中，那么数据相对是安全的，但是如果请求是Http请求，则敏感数据需要进行加密传输

### 默认算法

- 【AES】 只使用AES算法进行加解密操作，效率高，安全性一般，需要保护好秘钥
- 【RSA】 只使用RSA算法进行加解密操作，效率一般，安全性高，一般只需要保护好私钥即可
- 【RSA_AES】 RSA和AES算法结合使用，RSA只加密传输的AES秘钥，AES则用来加解密数据内容，这样效率较高，安全性也较好

_本工程内，默认加载AES算法作为默认算法_

开发人员可以配置算法，或者增加其它算法

### 配置算法

~~~yaml
# 配置已有算法
application:
  crypt:
    type: rsa
~~~

### 使用方式
- ApiCryptBody 出参和入参均加密
- ApiDecryptBody 入参加密
- ApiEncryptBody 出参加密

示例(samples包中):
~~~java
@RestController
@SuppressWarnings("all")
@Slf4j
public class TestCryptController {

    /**
     * 请求不加密，响应加密
     * @param user 请求参数
     * @return
     */
    @ApiEncryptBody
    @PostMapping("/encryptBody")
    public User encryptBody(@RequestBody User user) {
        log.info("==> 请求参数详情: {}", user.toString());
        return user;
    }

    /**
     * 请求加密，响应不加密
     * @param user 请求参数
     * @return
     */
    @ApiDecryptBody
    @PostMapping("/decryptBody")
    public User decryptBody(@RequestBody User user) {
        log.info("==> 请求参数详情: {}", user.toString());
        return user;
    }

    /**
     * 请求和响应都加密
     * @param user 请求参数
     * @return
     */
    @ApiCryptBody
    @PostMapping("/cryptBody")
    public User cryptBody(@RequestBody User user) {
        log.info("==> 请求参数详情: {}", user.toString());
        return user;
    }

}
~~~

### 自定义加密算法
如果需要进行设置自己的加密算法，则可以通过以下步骤接入本工程，在此假设要加入一个DES算法
1. 在算法枚举类中添加该算法名称
~~~java
/**
 * <h1>加解密算法类型</h1>
 * @author xzbcode
 */
public enum AlgorithmType {

    RSA("RSA"),
    AES("AES"),
    MIX("RSA_AES"),
    // 添加DES算法名称
    DES("DES");

    private String value;

    AlgorithmType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
~~~

2.实现一个加密处理器，实现 `ICryptHandler` 接口
~~~java
@Component
public class DesCryptHandler implements ICryptHandler {

    @Override
    public AlgorithmType getAlgorithmType() {
        // 返回该算法类型
        return AlgorithmType.DES;
    }

    @Override
    public String encrypt(HttpHeaders headers, String plainText) throws EncryptBodyException {
        // 实现对数据的加密操作，headers为请求头信息，plainText为需要加密的明文内容
        // ......
        // 最后返回加密后的数据
        return null;
    }

    @Override
    public String decrypt(HttpHeaders headers, String cipherText) throws DecryptBodyException {
        // 实现对数据的加密操作，headers为请求头信息，cipherText为需要解密的密文内容
        // ......
        // 最后返回解密后的数据
        return null;
    }

}    
~~~

3.在 `CryptoProperties` 中增加该算法的秘钥等需要配置的参数即可，如
~~~java
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application.crypt")
@SuppressWarnings("all")
public class CryptoProperties {

    private AlgorithmType type = AlgorithmType.AES;

    private AesProperties aes = new AesProperties();

    private RsaProperties rsa = new RsaProperties();

    private RsaAesProperties rsaAes = new RsaAesProperties();

    @Getter
    @Setter
    public class AesProperties {
        // AES密钥
        private String key = "ABCDEFGHIJKL0123";
        // AES偏移量
        private String iv = "1234567890123456";
    }

    // ..... 其它配置

    // 添加一个内部类，配置自己的算法参数即可，同时需要创建一个该类对象，方可在application.yaml中进行配置
    private DesProperties des = new DesProperties();

    @Getter
    @Setter
    public class DesProperties {
        // DES密钥
        private String key = "ABCDEFGHIJKL0123";
    }

}
~~~

### 异常关联
~~~text
`ApiCryptException`
    - `EncryptBodyException`
    - `DecryptBodyException`
~~~

### 注意事项
- 本工程内的秘钥等参数均可配置，示例中默认写在类中
- 加密异常会抛出 `EncryptBodyException` 异常
- 解密异常会抛出 `DecryptBodyException` 异常


