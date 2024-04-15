package com.github.riset_backend.chating.service;

import com.github.riset_backend.chating.dto.chatDto.ChatResponseDto;
import com.github.riset_backend.chating.dto.chatRoomDto.ChatRoomResponseDto;
import com.github.riset_backend.chating.dto.chatRoomDto.CreateChatRoomRequestDto;
import com.github.riset_backend.chating.entity.Chat;
import com.github.riset_backend.chating.entity.ChatRoom;
import com.github.riset_backend.chating.repository.ChatRepository;
import com.github.riset_backend.chating.repository.ChatRoomRepository;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final EmployeeRepository employeeRepository;
    private final ChatRepository chatRepository;

    public ChatRoomResponseDto createChatRoom(CreateChatRoomRequestDto dto) {


        List<Employee> employees = new ArrayList<>();

        dto.getMembers().forEach(employeeNo -> {
            Employee employee = employeeRepository.findByEmployeeNo(employeeNo).orElseThrow(
                    () -> new BusinessException(ErrorCode.NOT_FOUND_EMPLOYEE)
            );
            employees.add(employee);
        });

        log.info("employees = {}", employees);

//        LocalDateTime now =  new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();


        ChatRoom chatRoom = new ChatRoom(employees, now);

        log.info("ChatRoom = {}", chatRoom);

         ChatRoom createChatRoom = chatRoomRepository.save(chatRoom);

        log.info("createChatRoom = {}", createChatRoom);

         return new ChatRoomResponseDto(createChatRoom);
    }

    public List<ChatRoomResponseDto> getChatRoom(Long employeeNo) {
         List<ChatRoom> chatRooms = chatRoomRepository.findAllByMembersContains(1L);
         return chatRooms.stream().map(ChatRoomResponseDto::new).collect(Collectors.toList());
    }

    public boolean leaveChatRoom(Employee employee, String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_CHATROOM)
        );

        List<Employee> members = chatRoom.getMembers();
        List<Long> membersNo = members.stream().map(Employee::getEmployeeNo).collect(Collectors.toList());

        if (membersNo.contains(employee.getEmployeeNo())) {
            int index = membersNo.indexOf(employee.getEmployeeNo());
            members.remove(index);
            chatRoom.setMembers(members);
            chatRoomRepository.save(chatRoom);
            return true;
        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_CHATROOM_MEMBER);
        }

    }

    public List<ChatResponseDto> getChatRoomChat(String roomId) {
        List<Chat> chats = chatRepository.findAllByRoomId(roomId);
        return chats.stream().map(ChatResponseDto::new).collect(Collectors.toList());
    }

    public List<ChatResponseDto> getChatRoomChatOne(String roomId, String msg) {
       List<Chat> chats = chatRepository.findByRoomIdAndMsgContaining(roomId, msg);

       if (chats.isEmpty()) {
           throw new BusinessException(ErrorCode.NOT_FOUND_CHAT);
       }

        return chats.stream().map(ChatResponseDto::new).collect(Collectors.toList());
    }
}
