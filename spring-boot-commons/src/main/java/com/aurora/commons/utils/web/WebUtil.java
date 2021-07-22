package com.aurora.commons.utils.web;

import javax.servlet.http.HttpServletRequest;

/**
 * Web工具类
 * @author xzbcode
 */
public class WebUtil {

    private final static String IP_UNKNOWN = "unknown";

    /**
     * 获取客户端的浏览器类型
     * @param request
     * @return
     */
    public static String getClientBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        // 未知
        if (userAgent==null || "".equals(userAgent)) {
            return "Unknown";
        }
        // Chrome 浏览器？
        if (userAgent.contains("Chrome")
                && userAgent.contains("Safari")) {
            return "Chrome";
        }
        // Firefox 浏览器？
        if (userAgent.contains("Firefox")) {
            return "Firefox";
        }
        // Edge 浏览器？
        if (userAgent.contains("Edge")) {
            return "Edge";
        }
        // Safari 浏览器？
        if (userAgent.contains("Safari")
                && !userAgent.contains("Chrome")) {
            return "Safari";
        }
        // Opera 浏览器？
        if (userAgent.contains("Opera")) {
            return "Opera";
        }
        // IE 浏览器？ 11之前版本用MSIE判断，11及之后版本用Trident判断
        if ((userAgent.contains("MSIE") && userAgent.contains("compatible"))
                || userAgent.contains("Trident")) {
            return "IE";
        }
        // 其它浏览器？
        return "Others";
    }

    /**
     * 获取域名
     * @return
     */
    public static String getDomainName(String url) {
        String result = "";
        int j = 0, startIndex = 0, endIndex = 0;
        for (int i = 0; i < url.length(); i++) {
            if (url.charAt(i) == '/') {
                j++;
                if (j == 2)
                    startIndex = i;
                else if (j == 3)
                    endIndex = i;
            }

        }
        result = url.substring(startIndex + 1, endIndex);
        return result;
    }

    /**
     * 获取客户端IP地址
     * @param request
     * @return
     */
    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多重代理，则取第一个ip
        if (ip.contains(",")) {
            return ip.split(",")[0];
        }
        // 本地ip
        if (isLocalIp(ip)) {
            return "127.0.0.1";
        }
        return ip;
    }

    /**
     * 是否为本地IP地址
     * @param ip
     * @return
     */
    private static boolean isLocalIp(String ip) {
        return "0:0:0:0:0:0:0:1".equals(ip);
    }

    /**
     * 获取客户端操作系统
     * @param request
     * @return
     */
    public static String getClientOS(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        // 未知
        if (userAgent==null || "".equals(userAgent)) {
            return "Unknown";
        }
        // windows os
        if (isWindows10(userAgent)) {
            return "Windows 10";
        }
        if (isWindows7(userAgent)) {
            return "Windows 7";
        }
        if (isWindows8(userAgent)) {
            return "Windows 8";
        }
        if (isWindowsXP(userAgent)) {
            return "Windows XP";
        }
        if (isOtherWindows(userAgent)) {
            return "Windows OS";
        }
        // Mac OS
        if (userAgent.indexOf("Mac") > -1) {
            return "Mac OS";
        }
        // Unix OS
        if (userAgent.indexOf("Unix") > -1) {
            return "Unix OS";
        }
        // Solaris OS
        if (userAgent.indexOf("SunOS") > -1) {
            return "Solaris OS";
        }
        // Linux OS
        if (userAgent.indexOf("Linux")>-1 || userAgent.indexOf("Ubuntu") > 0) {
            return "Linux OS";
        }
        // IOS
        if (userAgent.indexOf("iPhone") > -1) {
            return "IOS";
        }
        // Android
        if (userAgent.indexOf("Android") > -1) {
            return "Android";
        }
        // Others
        return "Others";
    }

    private static boolean isWindowsXP(String userAgent) {
        return userAgent.indexOf("NT 5.1") > -1;
    }

    private static boolean isWindows7(String userAgent) {
        return userAgent.indexOf("NT 6.1") > -1;
    }

    private static boolean isWindows8(String userAgent) {
        return userAgent.indexOf("NT 6.2") > -1 || userAgent.indexOf("NT 6.3") > -1;
    }

    private static boolean isWindows10(String userAgent) {
        return userAgent.indexOf("NT 10") > -1;
    }

    private static boolean isOtherWindows(String userAgent) {
        return userAgent.indexOf("NT 5") > -1 || userAgent.indexOf("NT 6") > -1;
    }
}
