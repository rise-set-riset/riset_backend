package com.github.riset_backend.chating.controller;

import com.github.riset_backend.chating.dto.MessageSendDto;
import com.github.riset_backend.chating.entity.Chat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/message/{roomNum}")
    public void message(MessageSendDto chat, @DestinationVariable("roomNum") int roomNum) {
        log.info("요청");
        log.info("roomNum ={}", roomNum);
        messagingTemplate.convertAndSend("/sub/chat/message/" + roomNum, chat);
    }

}
