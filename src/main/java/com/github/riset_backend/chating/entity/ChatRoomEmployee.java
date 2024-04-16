package com.github.riset_backend.chating.entity;

import com.github.riset_backend.chating.entity.chatRoom.ChatRoom;
import com.github.riset_backend.login.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chat_room_employee")
@DynamicUpdate
public class ChatRoomEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoom_employee_id")
    private Long chatRoomEmployeeId;

    @ManyToOne
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "employee_no")
    private Employee employee;

    @Column(name = "del_yn")
    private String deleted;

    public ChatRoomEmployee(ChatRoom chatRoom, Employee employee) {
        this.chatRoom = chatRoom;
        this.employee = employee;
    }

    public ChatRoomEmployee toLeave (String deleted) {
        this.deleted = deleted;
        return this;
    }
}
