package com.aurora.validation.core.contraint.regex;

import java.util.regex.Pattern;

/**
 * 常用正则池
 * @author xzbcode
 */
public class PatternPool {

    // 数字
    public static final Pattern NUMBERS = Pattern.compile("\\d+");
    // 字母(大小写)
    public static final Pattern WORD = Pattern.compile("[a-zA-Z]+$");
    // 大写字母
    public static final Pattern UPPERCASE_WORD = Pattern.compile("[A-Z]+$");
    // 小写字母
    public static final Pattern LOWERCASE_WORD = Pattern.compile("[a-z]+$");
    // 字母数字下划线
    public static final Pattern WORD_NUMBER_UNDERLINE = Pattern.compile("^[0-9a-zA-Z_]{1,}$");

    // IPV4
    public static final Pattern IPV4 = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
    // IPV6
    public static final Pattern IPV6 = Pattern.compile("(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]+|::(ffff(:0{1,4})?:)?((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9]))");
    // 金额
    public static final Pattern MONEY = Pattern.compile("^(\\d+(?:\\.\\d+)?)$");
    // 邮箱地址
    public static final Pattern EMAIL = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])", 2);

    // URL地址
    public static final Pattern URL = Pattern.compile("[a-zA-z]+://[^\\s]*");
    // HTTP或HTTPS地址
    public static final Pattern URL_HTTP = Pattern.compile("(https://|http://)?([\\w-]+\\.)+[\\w-]+(:\\d+)*(/[\\w- ./?%&=]*)?");
    // MAC地址
    public static final Pattern MAC_ADDRESS = Pattern.compile("((?:[A-F0-9]{1,2}[:-]){5}[A-F0-9]{1,2})|(?:0x)(\\d{12})(?:.+ETHER)", 2);
    // 端口地址 0-65535
    public static final Pattern PORT = Pattern.compile("^(\\d|[1-5]\\d{4}|6[1-4]\\d{3}|65[1-4]\\d{2}|655[1-2]\\d|6553[1-5])$");

    // 中国-手机号码
    public static final Pattern CHINESE_PHONE_NUMBER = Pattern.compile("(?:0|86|\\+86)?1[3-9]\\d{9}");
    // 中国-车牌号
    public static final Pattern CHINESE_PLATE_NUMBER = Pattern.compile("^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[ABCDEFGHJK])|([ABCDEFGHJK]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领]\\d{3}\\d{1,3}[领])|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$");
    // 中国-身份证号码
    public static final Pattern CHINESE_ID_NUMBER = Pattern.compile("[1-9]\\d{5}[1-2]\\d{3}((0\\d)|(1[0-2]))(([012]\\d)|3[0-1])\\d{3}(\\d|X|x)");
    // 中国-行政区域代码
    public static final Pattern CHINESE_AREA_CODE = Pattern.compile("[1-8][1-7]\\d{4}");
    // 中国-邮政编码
    public static final Pattern CHINESE_ZIP_CODE = Pattern.compile("[1-9]\\d{5}(?!\\d)");


    // 中文
    public static final Pattern SIMPLE_CHINESE = Pattern.compile("^[\\u2E80-\\u9FFF]+$");

}
