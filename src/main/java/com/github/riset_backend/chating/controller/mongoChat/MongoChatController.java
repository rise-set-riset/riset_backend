package com.github.riset_backend.chating.controller.mongoChat;

import com.github.riset_backend.chating.dto.MongoMessageSendDto;
import com.github.riset_backend.chating.service.MongoChat.MongoChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MongoChatController {

    private final MongoChatService mongoChatService;

    @MessageMapping("/chat/message/{roomId}")
    public void message(@DestinationVariable("roomId") String roomId, MongoMessageSendDto messageSendDto) throws IOException {

        mongoChatService.sendMessage(messageSendDto, roomId);
    }

}
