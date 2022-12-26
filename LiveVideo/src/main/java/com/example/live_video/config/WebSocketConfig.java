package com.example.live_video.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * websocket配置类,   注入ServerEndpointExporter，
 * 这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint(接入点/端点)
 */
//Component也行
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){

        return new ServerEndpointExporter();
    }
}
