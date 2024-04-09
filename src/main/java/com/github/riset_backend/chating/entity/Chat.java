package com.github.riset_backend.chating.entity;

import com.github.riset_backend.chating.dto.MessageSendDto;
import com.github.riset_backend.login.employee.entity.Employee;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "chat")
public class Chat {
    @Id
    private String id;
    private String roomId;
    private String msg;
    private Integer roomNum;
    private List<Long> employees;
    private Employee sender;
    private LocalDateTime date;

    public Chat(MessageSendDto messageSendDto, LocalDateTime date, Employee sender) {
        this.roomId = messageSendDto.getRoomId();
        this.msg = messageSendDto.getMsg();
        this.employees = messageSendDto.getEmployeesNo();
        this.sender = sender;
        this.date = date;
    }
}
