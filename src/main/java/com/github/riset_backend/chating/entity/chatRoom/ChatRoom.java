package com.github.riset_backend.chating.entity.chatRoom;

import com.github.riset_backend.chating.entity.ChatRoomEmployee;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chat_room")
@DynamicUpdate
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "charRoom_id")
    private Long chatRoomId;

    @Column(name = "create_at")
    private LocalDateTime crateAt;

    @Column(name = "del_yn")
    private String deleted;

    @Column(name = "chat_room_name")
    private String chatRoomName;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY)
    List<ChatRoomEmployee> chatRoomEmployees;

    public ChatRoom(LocalDateTime crateAt, String chatRoomName) {
        this.crateAt = crateAt;
        this.chatRoomName = chatRoomName;
    }
}
