package com.github.riset_backend.chating.repository;

import com.github.riset_backend.chating.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
}
