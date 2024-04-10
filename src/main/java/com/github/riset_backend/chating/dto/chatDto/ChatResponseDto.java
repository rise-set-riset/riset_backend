package com.github.riset_backend.chating.dto.chatDto;

import com.github.riset_backend.chating.entity.Chat;
import com.github.riset_backend.login.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseDto {
    private String roomId;
    private String msg;
    private List<Long> employees;
    private Employee sender;
    private LocalDateTime date;
    private List<String> fileNames;

    public ChatResponseDto(Chat chat) {
        this.roomId = chat.getRoomId();
        this.msg = chat.getMsg();
        this.employees = chat.getEmployees();
        this.sender = chat.getSender();
        this.date = chat.getDate();
        this.fileNames = chat.getFilesName();
    }

}
