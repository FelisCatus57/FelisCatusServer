package com.example.backend.domain.websocket.config;

import com.example.backend.domain.websocket.handler.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //port: 9090 설정
        //setAllowedOringins("*")으로 모든 ip에서 접속 가능하다.
        registry.addHandler(webSocketHandler, "/api/websocket/chat").setAllowedOrigins("*");
    }
}
