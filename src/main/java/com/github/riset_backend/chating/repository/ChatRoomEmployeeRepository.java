package com.github.riset_backend.chating.repository;

import com.github.riset_backend.chating.entity.ChatRoomEmployee;
import com.github.riset_backend.chating.entity.chatRoom.ChatRoom;
import com.github.riset_backend.login.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomEmployeeRepository extends JpaRepository<ChatRoomEmployee, Long> {

    List<ChatRoomEmployee> findAllByEmployeeAndDeleted(Employee employee, String deleted);

    Optional<ChatRoomEmployee> findByChatRoomAndEmployee(ChatRoom chatRoom, Employee employee);

}
