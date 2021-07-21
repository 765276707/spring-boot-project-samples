package com.aurora.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * websocket配置
 * @author xzbcode
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket") // 端点路径
                .setAllowedOrigins("*") // 支持跨域
                .withSockJS(); // 支持降级
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 设置消息代理的前缀
        registry.enableSimpleBroker("/topic", "/queue");
        // 设置一对一发送消息的前缀
        registry.setUserDestinationPrefix("/user");
        // web socket 公共前缀
        registry.setApplicationDestinationPrefixes("/socket");
    }

}
