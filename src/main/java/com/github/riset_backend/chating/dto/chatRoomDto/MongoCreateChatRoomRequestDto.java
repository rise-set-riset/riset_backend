package com.github.riset_backend.chating.dto.chatRoomDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MongoCreateChatRoomRequestDto {
    private List<Long> members;
//    private LocalDateTime createAt;
}
