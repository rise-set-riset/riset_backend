package com.github.riset_backend.chating.repository.chatRoom;

import com.github.riset_backend.chating.entity.ChatRoomEmployee;
import com.github.riset_backend.chating.entity.chatRoom.ChatRoom;
import com.github.riset_backend.login.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
