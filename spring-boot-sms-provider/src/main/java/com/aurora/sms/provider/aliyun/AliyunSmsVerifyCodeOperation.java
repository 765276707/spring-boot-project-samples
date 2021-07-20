package com.aurora.sms.provider.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aurora.sms.core.ISmsVerifyCodeOperation;
import com.aurora.sms.exception.SmsSendException;
import com.aurora.sms.provider.aliyun.support.AliyunSmsProperties;
import com.aurora.sms.provider.aliyun.support.SmsCacheProvider;
import org.springframework.util.StringUtils;

/**
 * 阿里云短信验证码的实现
 * @author xzbcode
 */
public class AliyunSmsVerifyCodeOperation implements ISmsVerifyCodeOperation {

    private final AliyunSmsProperties aliyunSmsProp;
    private final SmsCacheProvider smsCacheProvider;

    public AliyunSmsVerifyCodeOperation(AliyunSmsProperties aliyunSmsProp, SmsCacheProvider smsCacheProvider) {
        this.aliyunSmsProp = aliyunSmsProp;
        this.smsCacheProvider = smsCacheProvider;
    }

    @Override
    public void sendVerifyCode(String phoneNumber, String smsCodeTemplate, String smsSignName) {
        // 可自助调整HTTP超时时间
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
        request.setTemplateCode(smsCodeTemplate);
        // 可选：模板中的变量替换JSON串，如模板内容为"您的XXX验证码为${code}"时，此处的值为
        String code = this.generatorCode();
        request.setTemplateParam("{\"code\": "+ code +"}");

        // 发送短信
        SendSmsResponse sendResp = null;
        try {
            sendResp = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            throw new SmsSendException("短信验证码发送失败", e);
        }

        // 解析响应
        if (!"OK".equals(sendResp.getCode())) {
            throw new SmsSendException(
                    String.format("短信验证码/校验码发送失败，错误码：{}，错误信息：{}", sendResp.getCode(), sendResp.getMessage()));
        }

        //　发送成功，存入缓存
        smsCacheProvider.putCodeInCache(phoneNumber, smsCodeTemplate, code);
    }


    @Override
    public boolean checkVerifyCode(String phoneNumber, String smsCodeTemplate, String smsCode) {
        // 参数为空返回false
        if (StringUtils.isEmpty(phoneNumber) || StringUtils.isEmpty(smsCode)) {
            return false;
        }
        // 查询 ID
        String smsCodeInCache = smsCacheProvider.getCodeFromCache(phoneNumber, smsCodeTemplate);
        // 验证码内容是否一致
        return smsCode.equals(smsCodeInCache);
    }


    /**
     * 短信验证码生成规则
     * @return
     */
    private String generatorCode() {
//        return String.valueOf((int)((Math.random()*9+1)*100000));
        return "123456";
    }
}
