package com.aurora.websocket.service.impl;

import com.aurora.websocket.argument.SendBatchParam;
import com.aurora.websocket.argument.SendParam;
import com.aurora.websocket.constant.SocketConstant;
import com.aurora.websocket.service.ISocketService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class SocketServiceImpl implements ISocketService {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void sendToUser(SendParam sendParam) {
        // 构建发送目标
        String destination = SocketConstant.USER + sendParam.getDestination();
        // 发送消息
        simpMessagingTemplate.convertAndSend(destination, sendParam.getBody());
    }

    @Override
    public void sendToUserBatch(SendBatchParam sendBatchParam) {
        String origin = sendBatchParam.getOrigin();
        String body = sendBatchParam.getBody();
        Set<String> destinations = sendBatchParam.getDestinations();
        for (String destination : destinations) {
            this.sendToUser(new SendParam(origin, destination, body));
        }
    }

    @Override
    public void sendToAll(SendParam sendParam) {
        // 发送消息
        simpMessagingTemplate.convertAndSend(SocketConstant.ALL_DESTINATION, sendParam.getBody());
    }

}
