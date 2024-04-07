package com.github.riset_backend.chating.controller;

import com.github.riset_backend.chating.dto.CreateChatRoomRequestDto;
import com.github.riset_backend.chating.entity.ChatRoom;
import com.github.riset_backend.chating.repository.ChatRoomRepository;
import com.github.riset_backend.chating.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/room")
    public ResponseEntity<ChatRoom> createChatRoom (@RequestBody CreateChatRoomRequestDto dto) {
        ChatRoom chatRoom = chatRoomService.createChatRoom(dto);
        return ResponseEntity.ok(chatRoom);
    }

}
