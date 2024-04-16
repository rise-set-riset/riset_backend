package com.github.riset_backend.chating.dto.chatDto;

import com.github.riset_backend.chating.entity.chat.Chat;
import com.github.riset_backend.chating.entity.chatRoom.ChatRoom;
import com.github.riset_backend.login.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseDto {

    private Long chatRoomId;
    private String msg;
    private List<Employee> members;
    private Employee sender;
    private LocalDateTime date;
    private String fileNames;

    public ChatResponseDto(Chat chat, List<Employee> members) {
        this.chatRoomId = chat.getChatRoom().getChatRoomId();
        this.msg = chat.getMsg();
        this.members = members;
        this.sender = chat.getSender();
        this.date = chat.getCreateAt();
        this.fileNames = chat.getFilesName();
    }
}
