package com.example.backend.domain.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.backend.domain.websocket.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.stereotype.Component;

//자바 기본라이브러리 추가
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;

    //현재 연결된 세션들
    private final Set<WebSocketSession> Sessions = new HashSet<>();

    //chatRoomId:{session1, session2}
    private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();

    // 소켓 연결 확인
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{}연결완료", session.getId());
        Sessions.add(session);
    }

    // 소켓 통신 시 메세지의 전송제어부분
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload: {}", payload);

        // 페이로드 -> chatMessageDto 변환
        // 26 : private final ObjectMapper objectMapper;
        ChatMessageDTO chatMessageDto = objectMapper.readValue(payload, ChatMessageDTO.class);
        log.info("session {}", chatMessageDto.toString());

        //최적화를 위해 chatRooId로 할당
        Long chatRoomId = chatMessageDto.getChatRoomId();

        // 메모리 상에 채팅방에 대한 세션이 없다면 생성
        if(!chatRoomSessionMap.containsKey(chatRoomId)) {
            chatRoomSessionMap.put(chatRoomId, new HashSet<>());
        }

        Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatRoomId);

        if(chatMessageDto.getMessageType().equals(ChatMessageDTO.MessageType.ENTER)) {
            chatRoomSession.add(session);
        }

        if(chatRoomSession.size() >= 3) {
            removeClosedSession(chatRoomSession);
        }

        sendMessageToChatRoom(chatMessageDto, chatRoomSession);
    }

    //소켓 종료 확인
    //CloseStatus status
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // TODO Auto-genrated method stub
        log.info("{} 연결 끊김", session.getId());
        Sessions.remove(session);
    }

    // 채팅 기능 함수
    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.removeIf(sess -> !Sessions.contains(sess));
    }

    private void sendMessageToChatRoom(ChatMessageDTO chatMessageDto, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatMessageDto));
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
