package com.aurora.sms;

import com.aurora.sms.core.ISmsOperator;
import com.aurora.sms.exception.SmsSendException;
import com.aurora.sms.provider.aliyun.support.AliyunSmsConstant;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@Slf4j
@SpringBootTest
class SmsSendTests {

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
