package com.aurora.websocket.constant;

public class SocketConstant {

    // 消息类型
    public final static String QUEUE = "/queue";
    public final static String TOPIC = "/topic";

    // 自己
    public static final String USER = QUEUE + "/user/";
    // 全部用户
    public static final String ALL_DESTINATION = TOPIC + "/public";

}
