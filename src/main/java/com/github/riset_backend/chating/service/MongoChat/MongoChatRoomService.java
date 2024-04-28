package com.github.riset_backend.chating.service.MongoChat;

import com.github.riset_backend.chating.dto.chatDto.ChatResponseDto;
import com.github.riset_backend.chating.dto.chatDto.MongoChatResponseDto;
import com.github.riset_backend.chating.dto.chatRoomDto.ChatRoomResponseDto;
import com.github.riset_backend.chating.dto.chatRoomDto.MongoChatRoomResponseDto;
import com.github.riset_backend.chating.dto.chatRoomDto.MongoCreateChatRoomRequestDto;
import com.github.riset_backend.chating.dto.chatRoomDto.UpdateChatRoomDto;
import com.github.riset_backend.chating.entity.ChatRoomEmployee;
import com.github.riset_backend.chating.entity.chat.Chat;
import com.github.riset_backend.chating.entity.chat.MongoChat;
import com.github.riset_backend.chating.entity.chatRoom.ChatRoom;
import com.github.riset_backend.chating.entity.chatRoom.MongoChatRoom;
import com.github.riset_backend.chating.repository.ChatRoomEmployeeRepository;
import com.github.riset_backend.chating.repository.chat.ChatRepository;
import com.github.riset_backend.chating.repository.chat.MongoChatRepository;
import com.github.riset_backend.chating.repository.chatRoom.ChatRoomRepository;
import com.github.riset_backend.chating.repository.chatRoom.MongoChatRoomRepository;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MongoChatRoomService {



    private final EmployeeRepository employeeRepository;
    private final MongoChatRoomRepository mongoChatRoomRepository;
    private final MongoChatRepository mongoChatRepository;

    @Transactional
    public MongoChatRoomResponseDto createChatRoom(MongoCreateChatRoomRequestDto dto) {
        List<Employee> employees = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        dto.getMembers().forEach(employeeNo -> {
            Employee employee = employeeRepository.findByEmployeeNo(employeeNo).orElseThrow(
                    () -> new BusinessException(ErrorCode.NOT_FOUND_EMPLOYEE)
            );
            employees.add(employee);
        });

        MongoChatRoom mongoChatRoom = new MongoChatRoom(employees, now);
        MongoChatRoom createMongoChatRoom = mongoChatRoomRepository.save(mongoChatRoom);
        return new MongoChatRoomResponseDto(createMongoChatRoom);

    }

    @Transactional
    public List<MongoChatRoomResponseDto> getChatRoom(Employee employee) {
         List<MongoChatRoom> mongoChatRooms = mongoChatRoomRepository.findAllByMembersContains(employee.getEmployeeNo());
         return mongoChatRooms.stream().map(MongoChatRoomResponseDto::new).collect(Collectors.toList());
    }

    public boolean leaveChatRoom(Employee employee, Long roomId) {
        MongoChatRoom mongoChatRoom = mongoChatRoomRepository.findById(roomId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_CHATROOM)
        );

        List<Employee> members = mongoChatRoom.getMembers();
        List<Long> membersNo = members.stream().map(Employee::getEmployeeNo).collect(Collectors.toList());

        if (membersNo.contains(employee.getEmployeeNo())) {
            int index = membersNo.indexOf(employee.getEmployeeNo());
            members.remove(index);
            mongoChatRoom.setMembers(members);
            mongoChatRoomRepository.save(mongoChatRoom);
            return true;
        } else {
            throw new BusinessException(ErrorCode.NOT_FOUND_CHATROOM_MEMBER);
        }

    }



    @Transactional
    public List<MongoChatResponseDto> getChatRoomChat(String roomId) {
        List<MongoChat> mongoChats =mongoChatRepository.findAllByRoomId(roomId);
        return mongoChats.stream().map(MongoChatResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public List<MongoChatResponseDto> getChatRoomChatOne(String roomId, String msg) {
       List<MongoChat> mongoChats = mongoChatRepository.findByRoomIdAndMsgContaining(roomId, msg);

       if (mongoChats.isEmpty()) {
           throw new BusinessException(ErrorCode.NOT_FOUND_CHAT);
       }

        return mongoChats.stream().map(MongoChatResponseDto::new).collect(Collectors.toList());
    }
}
