package com.github.riset_backend.chating.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id
    private String Id;
    private String name;
    private List<String> members;
    private Integer roomNum;

    public ChatRoom(List<String> members, Integer roomNum) {
        this.members = members;
        this.roomNum = roomNum;
    }
}
