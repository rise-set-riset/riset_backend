package com.github.riset_backend.chating.entity.chat;

import com.github.riset_backend.chating.dto.MessageSendDto;
import com.github.riset_backend.chating.entity.ChatRoomEmployee;
import com.github.riset_backend.chating.entity.chatRoom.ChatRoom;
import com.github.riset_backend.login.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chat")
@DynamicUpdate
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long chatId;

    @ManyToOne
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "employee_no")
    private Employee sender;

    @Column(name = "mag")
    private String msg;

    @Column(name = "filesName")
    private String filesName;

    @Column(name = "del_yn")
    private String deleted;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    public Chat(ChatRoom chatRoom , MessageSendDto dto, LocalDateTime now, Employee sender, String filesName) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.msg = dto.getMsg();
        this.filesName = filesName;
        this.createAt = now;
    }
}
