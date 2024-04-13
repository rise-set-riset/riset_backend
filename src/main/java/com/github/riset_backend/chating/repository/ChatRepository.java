package com.github.riset_backend.chating.repository;

import com.github.riset_backend.chating.entity.Chat;
import com.github.riset_backend.chating.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends MongoRepository<Chat, String> {
    List<Chat> findAllByRoomId(String roomId);

    List<Chat> findByRoomIdAndMsgContaining(String roomId, String msg);
}
