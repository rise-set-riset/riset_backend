package com.github.riset_backend.writeBoard.reply.dto;

import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.writeBoard.reply.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponseDto {

    private Long id;
    private Employee employee;
    private String content;
    private LocalDateTime date;

    public ReplyResponseDto(Reply reply) {
        this.id = reply.getReplyNo();
        this.employee = reply.getEmployee();
        this.content = reply.getContent();
        this.date = reply.getCreateAt();
    }
}
