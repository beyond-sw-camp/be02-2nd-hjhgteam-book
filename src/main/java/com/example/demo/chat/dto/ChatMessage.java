package com.example.demo.chat.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    // 메시지 타입 : 입장, 채팅
    enum MessageType {
        ENTER, TEXT, QUIT
    }

    private MessageType type; // 메시지 타입
    private String roomName; // 원하는 웹툰
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
}
