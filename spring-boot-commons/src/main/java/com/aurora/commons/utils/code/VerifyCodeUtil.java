package com.aurora.commons.utils.code;

import java.util.Random;
import java.util.UUID;

/**
 * 验证码工具类
 * @author xzb
 */
public class VerifyCodeUtil {

    // 默认的字符范围
    private static final String DEFAULT_CHAR_SOURCES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";


    /**
     * 生成验证码
     * @param verifySize 验证码长度
     * @return String
     */
    public static String generateVerifyCode(int verifySize){
        int codesLen = DEFAULT_CHAR_SOURCES.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for(int i = 0; i < verifySize; i++){
            verifyCode.append(DEFAULT_CHAR_SOURCES.charAt(rand.nextInt(codesLen-1)));
        }
        return verifyCode.toString();
    }


    /**
     * 生成验证码（指定字符范围）
     * @param verifySize 验证码长度
     * @param sources 字符范围
     * @return String
     */
    public static String generateVerifyCode(int verifySize, String sources){
        if(sources == null || sources.length() == 0){
            sources = DEFAULT_CHAR_SOURCES;
        }
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for(int i = 0; i < verifySize; i++){
            verifyCode.append(sources.charAt(rand.nextInt(codesLen-1)));
        }
        return verifyCode.toString();
    }


    /**
     * 生成验证码编号
     * @return String
     */
    public static String generateVerifyId() {
        return UUID.randomUUID().toString().trim()
                .replace("-", "").toLowerCase();
    }

}
