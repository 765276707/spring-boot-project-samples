package com.aurora.security.core.cipher;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 签名工具类
 * @author xzbcode
 */
public class DigestUtil {

    /**
     * 速度：MD5 > SHA-1 > SHA-256
     * 安全性：MD5 < SHA-1 << SHA-256
     * 哈希值长度：MD5(128bit) < SHA-1(160bit) < SHA-256(256bit)
     */

    /**
     * md5 签名
     * @param origin 签名内容
     * @param charset 字符集
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String md5Encode(String origin, String charset)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return HexUtil.bytesToHexStr(md.digest(origin.getBytes(charset)));//正确的写法
    }


    /**
     * sha1 签名
     * @param origin 签名内容
     * @param charset 字符集
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha1Encode(String origin, String charset)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        return HexUtil.bytesToHexStr(md.digest(origin.getBytes(charset)));//正确的写法
    }


    /**
     * sha256 签名
     * @param origin 签名内容
     * @param charset 字符集
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha256Encode(String origin, String charset)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA256");
        return HexUtil.bytesToHexStr(md.digest(origin.getBytes(charset)));//正确的写法
    }
}
