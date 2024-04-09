package com.github.riset_backend.chating.repository;

import com.github.riset_backend.chating.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

    List<ChatRoom> findAllByMembersContains(Long employeeNo);

}
