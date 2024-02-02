package com.example.demo.chat.repository;

import com.example.demo.chat.dto.ChatRoomListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomListEntity, Long> {
    Optional<ChatRoomListEntity> findByRoomName(String roomName);
}
