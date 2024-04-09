package com.github.riset_backend.chating.controller;

import com.github.riset_backend.chating.dto.MessageSendDto;
import com.github.riset_backend.chating.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat/message/{roomId}")
    public void message(@DestinationVariable("roomId") String roomId, MessageSendDto messageSendDto) {
        log.info("file ={}", messageSendDto.getBase64File());
        log.info("확인");
        chatService.sendMessage(messageSendDto, roomId);
    }

}
