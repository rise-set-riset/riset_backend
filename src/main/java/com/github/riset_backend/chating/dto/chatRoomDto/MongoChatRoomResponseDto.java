package com.github.riset_backend.chating.dto.chatRoomDto;

import com.github.riset_backend.chating.entity.chatRoom.MongoChatRoom;
import com.github.riset_backend.login.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MongoChatRoomResponseDto {

    private String id;
//    private int roomNum;
//    private String chatRoomName;
    private List<Employee> members;
    private LocalDateTime date;

    public MongoChatRoomResponseDto(MongoChatRoom mongoChatRoom) {
        this.id = mongoChatRoom.getId();
        this.members = mongoChatRoom.getMembers();
        this.date = mongoChatRoom.getDate();
    }
}
