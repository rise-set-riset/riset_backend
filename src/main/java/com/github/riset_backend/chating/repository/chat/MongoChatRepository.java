package com.github.riset_backend.chating.repository.chat;

import com.github.riset_backend.chating.entity.chat.MongoChat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoChatRepository extends MongoRepository<MongoChat, String> {
    List<MongoChat> findAllByRoomId(String roomId);

    List<MongoChat> findByRoomIdAndMsgContaining(String roomId, String msg);
}
