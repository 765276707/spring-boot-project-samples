package com.aurora.sms.provider.aliyun.util;

import com.aurora.sms.exception.SmsSendException;
import java.util.Set;

/**
 * 字符串工具类
 * @author xzbcode
 */
public class StringUtil {

    public static String join(Set<String> phoneNumbers, String joinChar) {
        if (phoneNumbers.size() <= 0) {
            throw new SmsSendException("手机号集合不能为空");
        }
        StringBuilder sb = new StringBuilder();
        for (String phoneNumber : phoneNumbers) {
            if (!"".equals(phoneNumber)) {
                sb.append(phoneNumber).append(",");
            }
        }
        String result = sb.toString();
        if (result.lastIndexOf(joinChar) > 0) {
            result = result.substring(0, result.length()-1);
        }
        return result;
    }
}
