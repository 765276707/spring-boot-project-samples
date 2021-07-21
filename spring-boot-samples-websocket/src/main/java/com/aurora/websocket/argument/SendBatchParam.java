package com.aurora.websocket.argument;

import lombok.Data;

import java.util.Set;

/**
 * 消息批量发送参数
 * @author xzbcode
 */
@Data
public class SendBatchParam {

    // 发送源
    private String origin;

    // 接收目标群
    private Set<String> destinations;

    // 消息内容
    private String body;

}
