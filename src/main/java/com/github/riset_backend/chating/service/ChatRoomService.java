package com.github.riset_backend.chating.service;

import com.github.riset_backend.chating.dto.CreateChatRoomRequestDto;
import com.github.riset_backend.chating.entity.ChatRoom;
import com.github.riset_backend.chating.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom createChatRoom(CreateChatRoomRequestDto dto) {
         ChatRoom chatRoom = new ChatRoom(dto.getMembers(), 1);
         return chatRoomRepository.save(chatRoom);
    }
}
