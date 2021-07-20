## 工程简介
- 本项目的短信发送服务使用抽象工程模式进行设计，方便轻松的替换底层短信服务商
- 默认使用的是阿里云短信服务

### 需要引入的关键JAR包
~~~xml
<xml>
    <properties>
        <aliyun.java.sdk.core>4.5.8</aliyun.java.sdk.core>
        <aliyun.java.sdk.dysmsapi>1.1.0</aliyun.java.sdk.dysmsapi>
    </properties>
    
    <dependencies>
       <!-- aliyun-java-sdk-core -->
       <dependency>
           <groupId>com.aliyun</groupId>
           <artifactId>aliyun-java-sdk-core</artifactId>
           <version>${aliyun.java.sdk.core}</version>
       </dependency>
       
       <!-- aliyun-java-sdk-dysmsapi -->
       <dependency>
           <groupId>com.aliyun</groupId>
           <artifactId>aliyun-java-sdk-dysmsapi</artifactId>
           <version>${aliyun.java.sdk.dysmsapi}</version>
       </dependency> 
    </dependencies>
</xml>
~~~

### 使用方式

~~~java
@Slf4j
@SpringBootTest
class SpringBootSmsAliyunApplicationTests {

    @Autowired
    private ISmsOperator smsOperator;

    /**
     * 测试发送短信验证码
     */
    @Test
    void testSendSmsVerifyCode() {
        log.info("正在发送短信......");
        // 发送短信
        try {
            smsOperator.opsForVerifyCode()
                    .sendVerifyCode("185xxxxx469", AliyunSmsConstant.TEMPLATE_LOGIN_SMSCODE, AliyunSmsConstant.SIGNNAME_APP);
        } catch (SmsSendException e) {
            log.error("短信发送失败，详情：{}", e.getMessage());
        }
        log.info("短信发送完成......");
    }

    /**
     * 测试校验短信验证码
     */
    @Test
    void testCheckSmsVerifyCode() {
        // 模拟手机获取到的短信验证码
        String receivedVerifyCode = "123456";
        // 校验短信验证码
        boolean result = smsOperator.opsForVerifyCode()
                .checkVerifyCode("185xxxxx469", AliyunSmsConstant.TEMPLATE_LOGIN_SMSCODE, receivedVerifyCode);
        // 校验结果
        log.info("校验结果： {}", result?"有效":"无效");
    }

    /**
     * 测试发送短信通知
     */
    @Test
    void testSendSmsNotice() {
        log.info("正在发送短信......");
        // 发送短信
        try {
            smsOperator.opsForNotice()
                    .sendNotice("185xxxxx469", AliyunSmsConstant.TEMPLATE_LOGIN_SMSCODE,
                            AliyunSmsConstant.SIGNNAME_APP, "您的账户出现异常，请及时确认是否为本人操作");
        } catch (SmsSendException e) {
            log.error("短信发送失败，详情：{}", e.getMessage());
        }
        log.info("短信发送完成......");
    }

    /**
     * 测试批量发送短信通知
     */
    @Test
    void testSendSmsNoticeBatch() {
        log.info("正在发送短信......");
        // 构建批量短信集合
        Set<String> phoneNumbers = Sets.newHashSet();
        phoneNumbers.add("185xxxxx466");
        phoneNumbers.add("185xxxxx468");
        phoneNumbers.add("185xxxxx469");
        // 发送短信
        try {
            smsOperator.opsForNotice()
                    .sendNoticeBatch(phoneNumbers, AliyunSmsConstant.TEMPLATE_LOGIN_SMSCODE,
                            AliyunSmsConstant.SIGNNAME_APP, "您的账户出现异常，请及时确认是否为本人操作");
        } catch (SmsSendException e) {
            log.error("短信发送失败，详情：{}", e.getMessage());
        }
        log.info("短信发送完成......");
    }
}
~~~

### 实现其他短信服务商的代码

- 规定了三个标准接口
    1. `ISmsOperator` 是短信服务操作统一入口
    2. `ISmsVerifyCodeOperation` 是短信验证码业务
    3. `ISmsNoticeOperation` 是短信通知业务
- 注入自己实现的 `ISmsOperator` 在Spring容器中即可自动替换

案例：实现华为云短信服务，替换阿里云短信服务
~~~java
// 华为云短信入口
public class HuaweiSmsOperator implements ISmsOperator {
    
    @Override
    public ISmsVerifyCodeOperation opsForVerifyCode() {
        return new HuaweiSmsVerifyCodeOperation();
    }

    @Override
    public ISmsNoticeOperation opsForNotice() {
        return new HuaweiSmsNoticeOperation();
    }
    
}

// 华为云短信验证码业务
public class HuaweiSmsVerifyCodeOperation implements ISmsVerifyCodeOperation {
    
    @Override
    public void sendVerifyCode(String phoneNumber, String smsCodeTemplate, String smsSignName) {
        // 华为云短信验证码发送业务代码......
    }

    @Override
    public boolean checkVerifyCode(String phoneNumber, String smsCodeTemplate, String smsCode) {
        // 华为云短信验证码校验业务代码......
        return false;
    }
    
}

// 华为云短信通知业务
public class HuaweiSmsNoticeOperation implements ISmsNoticeOperation {

    @Override
    public void sendNotice(String phoneNumber, String smsNoticeTemplate, String smsSignName, String notice) {
        // 华为云短信通知单条发送业务代码......
    }

    @Override
    public void sendNoticeBatch(Set<String> phoneNumbers, String smsNoticeTemplate, String smsSignName, String notice) {
        // 华为云短信通知批量发送业务代码......
    }

}
~~~
最后注入 HuaweiSmsOperator 到容器中，或者更改自动装配内的代码
~~~java
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
        // return new AliyunSmsOperator();
        // 替换为华为云短信
        return new HuaweiSmsOperator();
    }

}
~~~    
