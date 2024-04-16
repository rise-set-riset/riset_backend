package com.github.riset_backend.chating.dto.chatRoomDto;

import com.github.riset_backend.chating.entity.ChatRoomEmployee;
import com.github.riset_backend.chating.entity.chatRoom.ChatRoom;
import com.github.riset_backend.login.employee.entity.Employee;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomResponseDto {
    private Long chatRoomId;
    private List<Employee> members;
    private LocalDateTime createAt;

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.chatRoomId = chatRoom.getChatRoomId();
        this.members = chatRoom.getChatRoomEmployees().stream().map(ChatRoomEmployee::getEmployee).collect(Collectors.toList());
        this.createAt = chatRoom.getCrateAt();
    }

    public ChatRoomResponseDto(ChatRoom chatRoom, List<Employee> employees) {
        this.chatRoomId = chatRoom.getChatRoomId();
        this.members = employees;
        this.createAt = chatRoom.getCrateAt();
    }
}
