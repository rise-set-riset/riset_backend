package com.github.riset_backend.chating.controller;

import com.github.riset_backend.chating.entity.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/message")
    public void message(Chat chat) {
        messagingTemplate.convertAndSend("/sub/room/" + chat.getRoomNum(), chat);
    }

}
