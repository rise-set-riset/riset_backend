package com.github.riset_backend.chating.repository.chatRoom;

import com.github.riset_backend.chating.entity.chatRoom.MongoChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoChatRoomRepository extends MongoRepository<MongoChatRoom, String> {

    List<MongoChatRoom> findAllByMembersContains(Long employeeNo);

}
