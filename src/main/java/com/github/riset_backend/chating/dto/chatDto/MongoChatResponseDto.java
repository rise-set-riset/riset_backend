package com.github.riset_backend.chating.dto.chatDto;

import com.github.riset_backend.chating.entity.chat.MongoChat;
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
public class MongoChatResponseDto {
    private String roomId;
    private String msg;
    private List<Long> employees;
    private Employee sender;
    private LocalDateTime date;
    private List<String> fileNames;

    public MongoChatResponseDto(MongoChat mongoChat) {
        this.roomId = mongoChat.getRoomId();
        this.msg = mongoChat.getMsg();
        this.employees = mongoChat.getEmployees();
        this.sender = mongoChat.getSender();
        this.date = mongoChat.getDate();
        this.fileNames = mongoChat.getFilesName();
    }
}
