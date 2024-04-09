package com.github.riset_backend.chating.service;

import com.github.riset_backend.chating.dto.chatDto.ChatResponseDto;
import com.github.riset_backend.chating.dto.MessageSendDto;
import com.github.riset_backend.chating.entity.Chat;
import com.github.riset_backend.chating.repository.ChatRepository;
import com.github.riset_backend.chating.repository.ChatRoomRepository;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final EmployeeRepository employeeRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    public void sendMessage(MessageSendDto messageSendDto, String roomId) {

        log.info("messageSendDto = {}", messageSendDto);
        log.info("roomId = {}", roomId);


        chatRoomRepository.findById(messageSendDto.getRoomId()).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_CHATROOM)
        );
//
//        if(!Objects.equals(messageSendDto.getRoomId(), roomId)) {
//            throw  new BusinessException(ErrorCode.DIFFERENT_CHATROOM);
//        }
//
        Employee sender = employeeRepository.findByEmployeeNo(messageSendDto.getSender()).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_EMPLOYEE)
        );

        LocalDateTime now =  new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Chat chat = new Chat(messageSendDto, now, sender);

        chatRepository.save(chat);
        messagingTemplate.convertAndSend("/sub/chat/message/" + roomId, new ChatResponseDto(chat));
    }
}
