    package com.example.demo.chat.service;


    import com.example.demo.chat.dto.ChatMessage;
    import com.example.demo.chat.dto.ChatMessageEntity;
    import com.example.demo.chat.dto.ChatRoom;
    import com.example.demo.chat.dto.ChatRoomListEntity;
    import com.example.demo.chat.repository.ChatMessageRepository;
    import com.example.demo.chat.repository.ChatRoomRepository;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    import org.springframework.web.socket.TextMessage;
    import org.springframework.web.socket.WebSocketSession;

    import javax.annotation.PostConstruct;
    import java.io.IOException;
    import java.util.*;

    @Slf4j
    @RequiredArgsConstructor
    @Service
    public class ChatService {

        private final ObjectMapper objectMapper;
        private Map<String, ChatRoom> chatRooms;
        private final ChatRoomRepository chatRoomRepository;
        private final ChatMessageRepository chatMessageRepository;
        private Set<WebSocketSession> sessions = new HashSet<>();


        @PostConstruct
        private void init() {
            chatRooms = new LinkedHashMap<>();
        }

        public List<ChatRoomListEntity> findAllRoom() {
            return chatRoomRepository.findAll();
        }

        public ChatRoom findRoomByName(String roomName) {
            Optional<ChatRoomListEntity> chatRoom1 = chatRoomRepository.findByRoomName(roomName);
            if(chatRoom1.isPresent()){
                ChatRoomListEntity chatRoomListEntity = chatRoom1.get();
                String roomId = chatRoomListEntity.getRoomId();
                return chatRooms.get(roomId);
            }else{
                return null;
            }
        }

        public ChatRoom createRoom(String name) {

            Optional<ChatRoomListEntity> originChat = chatRoomRepository.findByRoomName(name);
            if (originChat.isPresent()) {
                ChatRoom chatRoom = createChatRoom(name);

                ChatRoomListEntity origin = originChat.get();
                origin.setRoomId(chatRoom.getRoomId());

                chatRoomRepository.save(origin);
                return chatRoom;
            } else {
                ChatRoom chatRoom = createChatRoom(name);

                chatRoomRepository.save(ChatRoomListEntity.builder()
                        .roomId(chatRoom.getRoomId())
                        .roomName(name).build());
                return chatRoom;
            }
        }

        public ChatRoom createChatRoom(String name){
            if(chatRooms.get(name)!=null) {
                chatRooms.remove(name);
            }

            String randomId = UUID.randomUUID().toString();
            ChatRoom chatRoom = ChatRoom.builder()
                    .roomId(randomId)
                    .name(name)
                    .build();

            chatRooms.put(randomId, chatRoom);
            return chatRoom;
        }


        public void saveMessage(ChatMessage chatMessage) {
            chatMessageRepository.save(ChatMessageEntity.builder()
                    .message(chatMessage.getMessage())
                    .sender(chatMessage.getSender())
                    .roomName(chatMessage.getRoomName()).build());
        }

        public <T> void sendMessage(WebSocketSession session, T message) {
            try {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        public List<ChatMessageEntity> showAllMessage(String name){
            List<ChatMessageEntity> messages = chatMessageRepository.findAllByRoomName(name);
            return messages;
        }

    }
