package com.github.riset_backend.chating.service;

import com.github.riset_backend.chating.dto.chatRoomDto.ChatRoomResponseDto;
import com.github.riset_backend.chating.dto.chatRoomDto.CreateChatRoomRequestDto;
import com.github.riset_backend.chating.entity.ChatRoom;
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

    public ChatRoomResponseDto createChatRoom(CreateChatRoomRequestDto dto) {
        List<Employee> employees = new ArrayList<>();

        dto.getMembers().forEach(employeeNo -> {
            Employee employee = employeeRepository.findByEmployeeNo(employeeNo).orElseThrow(
                    () -> new BusinessException(ErrorCode.NOT_FOUND_EMPLOYEE)
            );
            employees.add(employee);
        });

        LocalDateTime now =  new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

         ChatRoom chatRoom = new ChatRoom(employees, now);

         ChatRoom createChatRoom = chatRoomRepository.save(chatRoom);
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
}
