package com.example.chat.repository;

import com.example.chat.model.dto.ChatRoomListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomListEntity, Long> {
    Optional<ChatRoomListEntity> findByRoomName(String roomName);
}
