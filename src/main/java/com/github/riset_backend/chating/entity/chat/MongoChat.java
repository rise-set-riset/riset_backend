package com.github.riset_backend.chating.entity.chat;

import com.github.riset_backend.chating.dto.MongoMessageSendDto;
import com.github.riset_backend.login.employee.entity.Employee;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat")
public class MongoChat {
    @Id
    private String id;
    private String roomId;
    private String msg;
    private List<Long> employees;
    private Employee sender;
    private List<String> filesName;
    private LocalDateTime date;

    public MongoChat(MongoMessageSendDto mongoMessageSendDto, LocalDateTime date, Employee sender) {
        this.roomId = mongoMessageSendDto.getRoomId();
        this.msg = mongoMessageSendDto.getMsg();
        this.employees = mongoMessageSendDto.getEmployeesNo();
        this.sender = sender;
        this.date = date;
        this.filesName = mongoMessageSendDto.getBase64File();
    }
}
