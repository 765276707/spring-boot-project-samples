package com.aurora.sms.provider.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aurora.sms.core.ISmsNoticeOperation;
import com.aurora.sms.exception.SmsSendException;
import com.aurora.sms.provider.aliyun.support.AliyunSmsProperties;
import com.aurora.sms.provider.aliyun.util.StringUtil;
import java.util.Set;

/**
 * 阿里云短信通知实现
 * @author xzbcode
 */
public class AliyunSmsNoticeOperation implements ISmsNoticeOperation {

    private final AliyunSmsProperties aliyunSmsProp;

    public AliyunSmsNoticeOperation(AliyunSmsProperties aliyunSmsProp) {
        this.aliyunSmsProp = aliyunSmsProp;
    }

    @Override
    public void sendNotice(String phoneNumber, String smsNoticeTemplate, String smsSignName, String notice) {
        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile(
                aliyunSmsProp.getRegionId(),
                aliyunSmsProp.getAccessKeyId(),
                aliyunSmsProp.getAccessKeySecret());
        DefaultProfile.addEndpoint(aliyunSmsProp.getRegionId(), aliyunSmsProp.getProduct(), aliyunSmsProp.getEndpoint());
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phoneNumber);

        // 必填：短信签名 和 短信模板
        request.setSignName(smsSignName);
        request.setTemplateCode(smsNoticeTemplate);

        // 可选：模板中的变量替换JSON串，如模板内容为"亲爱的${name}，您的验证码为${code}"时，此处的值为
        request.setTemplateParam("{\"message\": "+ notice +"}");

        // 发送短信
        SendSmsResponse sendResp = null;
        try {
            sendResp = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            // 抛出短信服务异常
            throw new SmsSendException("短信发送客户端异常", e);
        }
        // 确认结果
        // return "OK".equals(sendResp.getCode());
    }

    @Override
    public void sendNoticeBatch(Set<String> phoneNumbers, String smsNoticeTemplate, String smsSignName, String notice) {
        // 校验号码数量
        if (phoneNumbers.size()<=0 || phoneNumbers.size()>1000 ) {
            throw new IllegalArgumentException("手机号码数量不合法，取值1至1000之间");
        }
        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile(
                aliyunSmsProp.getRegionId(),
                aliyunSmsProp.getAccessKeyId(),
                aliyunSmsProp.getAccessKeySecret());
        DefaultProfile.addEndpoint(aliyunSmsProp.getRegionId(), aliyunSmsProp.getProduct(), aliyunSmsProp.getEndpoint());
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(StringUtil.join(phoneNumbers,","));

        // 必填：短信签名 和 短信模板
        request.setSignName(smsSignName);
        request.setTemplateCode(smsNoticeTemplate);

        // 可选：模板中的变量替换JSON串，如模板内容为"亲爱的${name}，您的验证码为${code}"时，此处的值为
        request.setTemplateParam("{\"message\": "+ notice +"}");

        // 发送短信
        SendSmsResponse sendResp = null;
        try {
            sendResp = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            // 抛出短信服务异常
            throw new SmsSendException("短信发送客户端异常", e);
        }
        // 确认结果
        // return "OK".equals(sendResp.getCode());
    }

}
