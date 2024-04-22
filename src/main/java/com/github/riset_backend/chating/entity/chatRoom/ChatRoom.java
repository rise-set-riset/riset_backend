package com.github.riset_backend.chating.entity.chatRoom;

import com.github.riset_backend.chating.entity.ChatRoomEmployee;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chat_room")
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "charRoom_id")
    private Long chatRoomId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime crateAt;

    @Column(name = "del_yn")
    private String deleted;

    @Column(name = "chat_room_name")
    private String chatRoomName;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY)
    List<ChatRoomEmployee> chatRoomEmployees;

    public ChatRoom(String chatRoomName) {

        this.chatRoomName = chatRoomName;
    }
}
