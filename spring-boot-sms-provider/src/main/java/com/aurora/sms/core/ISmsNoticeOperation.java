package com.aurora.sms.core;

import java.util.Set;

/**
 * 短信通知业务
 * @author xzbcode
 */
public interface ISmsNoticeOperation {

    /**
     * 发送短信通知
     * @param phoneNumber 手机号码
     * @param smsNoticeTemplate 短信通知模板
     * @param smsSignName 短信签名
     * @param notice 通知内容
     */
    void sendNotice(String phoneNumber, String smsNoticeTemplate, String smsSignName, String notice);

    /**
     * 批量发送短信通知
     * @param phoneNumbers 手机号码集合
     * @param smsNoticeTemplate 短信通知模板
     * @param smsSignName 短信签名
     * @param notice 通知内容
     */
    void sendNoticeBatch(Set<String> phoneNumbers, String smsNoticeTemplate, String smsSignName, String notice);

}
