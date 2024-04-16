package com.github.riset_backend.chating.entity.chatRoom;

import com.github.riset_backend.chating.entity.ChatRoomEmployee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chat_room")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "charRoom_id")
    private Long chatRoomId;

    @Column(name = "create_at")
    private LocalDateTime crateAt;

    @Column(name = "del_yn")
    private String deleted;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY)
    List<ChatRoomEmployee> chatRoomEmployees;

    public ChatRoom(LocalDateTime crateAt) {
        this.crateAt = crateAt;
    }
}
