package com.example.backend.domain.websocket.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType messageType; // 메시지 타입 ENTER, TALK
    private Long chatRoomId; // 방 번호
    private String senderId; // 메시지를 보내는 사람
    private String message; // 메시지

    /* 보내는 양식
        "messageType" : "TALK"
        "chatRoomId" : 1, //TODO sederId + receiverId 로 RoomId를 만드는 것도 검토
        "senderId" : 100 // 메시지 전송자의 Id
        "message" : "hello" // 메시지 내용
     */
}

