package com.aurora.websocket.controller;

import com.aurora.websocket.argument.SendParam;
import com.aurora.websocket.service.ISocketService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 消息接收
 * @author xzbcode
 */
@RestController
public class MessageController {

    @Resource
    private ISocketService socketService;

    /**
     * 发送私信
     * @param sendParam
     */
    @MessageMapping("/sendUser")
    public void sendUser(SendParam sendParam) {
        socketService.sendToUser(sendParam);
    }

    /**
     * 发送公告
     * @param sendParam
     */
    @MessageMapping("/sendAll")
    public void sendAll(SendParam sendParam) {
        socketService.sendToAll(sendParam);
    }

}
