package com.example.chat.controller;

import com.example.chat.model.dto.ChatRoom;
import com.example.chat.model.dto.ChatRoomListEntity;
import com.example.chat.service.ChatService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public List<ChatRoomListEntity> findAllRoom() {
        return chatService.findAllRoom();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity showChat(String name){
        return ResponseEntity.ok().body(chatService.showAllMessage(name));
    }
}
