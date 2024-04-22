package com.github.riset_backend.chating.controller;

import com.github.riset_backend.chating.dto.MessageSendDto;
import com.github.riset_backend.chating.dto.MongoMessageSendDto;
import com.github.riset_backend.chating.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat/message/{roomId}")
    public void message(@DestinationVariable("roomId") Long roomId, MessageSendDto messageSendDto) throws IOException {
        log.info("gjcng");
        chatService.sendMessage(messageSendDto, roomId);
    }

}
