package com.github.riset_backend.chating.dto.chatRoomDto;

import com.github.riset_backend.chating.dto.chatDto.ChatLastDto;
import com.github.riset_backend.chating.dto.chatDto.MemberResponseDto;
import com.github.riset_backend.chating.entity.ChatRoomEmployee;
import com.github.riset_backend.chating.entity.chat.Chat;
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
    private String chatRoomName;
    private List<MemberResponseDto> members;
    private LocalDateTime createAt;
    private ChatLastDto lastChat;

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.chatRoomId = chatRoom.getChatRoomId();
        this.chatRoomName = chatRoom.getChatRoomName();
        this.members = chatRoom.getChatRoomEmployees().stream().map(ChatRoomEmployee::getEmployee).map(MemberResponseDto::new).collect(Collectors.toList());
        this.createAt = chatRoom.getCrateAt();
    }

    public ChatRoomResponseDto(ChatRoom chatRoom, Chat chat) {
        this.chatRoomId = chatRoom.getChatRoomId();
        this.chatRoomName = chatRoom.getChatRoomName();
        this.members = chatRoom.getChatRoomEmployees().stream().map(ChatRoomEmployee::getEmployee).map(MemberResponseDto::new).collect(Collectors.toList());
        this.createAt = chatRoom.getCrateAt();
        this.lastChat = new ChatLastDto(chat);
    }

    public ChatRoomResponseDto(ChatRoom chatRoom, List<Employee> employees) {
        this.chatRoomId = chatRoom.getChatRoomId();
        this.chatRoomName = chatRoom.getChatRoomName();
        this.members = employees.stream().map(MemberResponseDto::new).collect(Collectors.toList());
        this.createAt = chatRoom.getCrateAt();
    }
}
