package com.github.riset_backend.chating.entity;

import com.github.riset_backend.login.employee.entity.Employee;
import jakarta.persistence.Column;
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
@Document(collection = "chatRoom")
public class ChatRoom {

    @Id
    private String id;
    private List<Employee> members;
    private LocalDateTime date;


    public ChatRoom(List<Employee> members, LocalDateTime date) {
        this.members = members;
        this.date = date;

    }
}
