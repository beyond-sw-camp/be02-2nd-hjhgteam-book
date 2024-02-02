package com.example.demo.chat.controller;


import com.example.demo.chat.dto.ChatRoom;
import com.example.demo.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
@CrossOrigin("*")
public class ChatController {

    private final ChatService chatService;

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ChatRoom createRoom(@RequestBody Map<String, String> data) {
        String name = data.get("name");
        return chatService.createRoom(name);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity findAllRoom() {
        return ResponseEntity.ok().body(chatService.findAllRoom());
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity showChat(@RequestParam String name){
        return ResponseEntity.ok().body(chatService.showAllMessage(name));
    }
}
