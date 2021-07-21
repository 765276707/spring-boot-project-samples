package com.aurora.websocket.service;

import com.aurora.websocket.argument.SendBatchParam;
import com.aurora.websocket.argument.SendParam;

/**
 * websocket消息发送业务
 * @author xzbcode
 */
public interface ISocketService {

    /**
     * 发送给用户消息
     * @param sendParam 发送参数
     */
    void sendToUser(SendParam sendParam);

    /**
     * 发送给批量用户消息
     * @param sendBatchParam 批量发送参数
     */
    void sendToUserBatch(SendBatchParam sendBatchParam);

    /**
     * 发送给全部用户消息
     * @param sendParam 发送参数
     */
    void sendToAll(SendParam sendParam);


}
