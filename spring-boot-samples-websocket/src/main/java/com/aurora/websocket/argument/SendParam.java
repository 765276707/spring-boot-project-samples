package com.aurora.websocket.argument;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 消息发送参数
 * @author xzbcode
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SendParam {

    // 发送源
    private String origin;

    // 接收目标
    private String destination;

    // 消息内容
    private String body;

}
