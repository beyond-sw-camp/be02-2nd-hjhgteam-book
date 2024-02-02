package com.example.demo.chat.dto;

import com.example.demo.chat.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@Getter
public class ChatRoom {

    private String roomId;
    private String roomName;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.roomName = name;
    }

    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) throws IOException {
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
            sendMessage(chatMessage, chatService);
            chatService.saveMessage(chatMessage);
        } else if (chatMessage.getType().equals(ChatMessage.MessageType.QUIT)) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 퇴장했습니다.");
            sendMessage(chatMessage, chatService);
            chatService.saveMessage(chatMessage);
            sessions.remove(session);
            session.close();
        }else{
            sendMessage(chatMessage, chatService);
            chatService.saveMessage(chatMessage);
        }
        }

        public <T > void sendMessage (T message, ChatService chatService){
            sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
        }
    }
