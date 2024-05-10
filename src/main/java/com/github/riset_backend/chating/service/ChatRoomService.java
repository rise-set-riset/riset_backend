package com.github.riset_backend.chating.service;

import com.github.riset_backend.chating.dto.chatDto.ChatResponseDto;
import com.github.riset_backend.chating.dto.chatRoomDto.ChatRoomResponseDto;
import com.github.riset_backend.chating.dto.chatRoomDto.MongoCreateChatRoomRequestDto;
import com.github.riset_backend.chating.dto.chatRoomDto.UpdateChatRoomDto;
import com.github.riset_backend.chating.entity.ChatRoomEmployee;
import com.github.riset_backend.chating.entity.chat.Chat;
import com.github.riset_backend.chating.entity.chatRoom.ChatRoom;
import com.github.riset_backend.chating.repository.ChatRoomEmployeeRepository;
import com.github.riset_backend.chating.repository.chat.ChatRepository;
import com.github.riset_backend.chating.repository.chatRoom.ChatRoomRepository;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomService {



    private final EmployeeRepository employeeRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomEmployeeRepository chatRoomEmployeeRepository;
    private final ChatRepository chatRepository;

    //    private final MongoChatRoomRepository mongoChatRoomRepository;
//    private final MongoChatRepository mongoChatRepository;

    @Transactional
    public ChatRoomResponseDto updateChatRoomName(Employee employee, Long chatRoomId, UpdateChatRoomDto dto) {

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_CHATROOM)
        );

        chatRoom.setChatRoomName(dto.getChatRoomName());
        ChatRoom updateRoom = chatRoomRepository.save(chatRoom);

        return new ChatRoomResponseDto(updateRoom);
    }


    @Transactional
    public ChatRoomResponseDto createChatRoom(MongoCreateChatRoomRequestDto dto) {
        List<Employee> employees = new ArrayList<>();

        dto.getMembers().forEach(employeeNo -> {
            Employee employee = employeeRepository.findByEmployeeNo(employeeNo).orElseThrow(
                    () -> new BusinessException(ErrorCode.NOT_FOUND_EMPLOYEE)
            );
            employees.add(employee);
        });

//        LocalDateTime now = LocalDateTime.now();
//        ZoneId koreaZoneId = ZoneId.of("Asia/Seoul");
//        ZonedDateTime nowInKorea = now.atZone(koreaZoneId);

        List<String> strings = employees.stream().map(Employee::getName).toList();
        String chatRoomName = strings.stream().collect(Collectors.joining(" "));

        ChatRoom chatRoom = new ChatRoom(chatRoomName);
        ChatRoom newChatRoom = chatRoomRepository.save(chatRoom);

        List<Employee> employeeList = new ArrayList<>();

        employees.forEach(employee -> {
            ChatRoomEmployee chatRoomEmployee = new ChatRoomEmployee(newChatRoom, employee);
            ChatRoomEmployee chatRoomEmployee1 = chatRoomEmployeeRepository.save(chatRoomEmployee);
            employeeList.add(chatRoomEmployee1.getEmployee());
        });

        return new ChatRoomResponseDto(newChatRoom, employeeList);

//
//        MongoChatRoom mongoChatRoom = new MongoChatRoom(employees, now);
//        MongoChatRoom createMongoChatRoom = mongoChatRoomRepository.save(mongoChatRoom);
//        return new MongoChatRoomResponseDto(createMongoChatRoom);


    }

    @Transactional
    public List<ChatRoomResponseDto> getChatRoom(Employee employee) {
        List<ChatRoom> chatRooms = chatRoomEmployeeRepository.findAllByEmployeeAndDeleted(employee, null).stream().map(ChatRoomEmployee::getChatRoom).toList();
        return chatRooms.stream().map(chatRoom -> {
           List<Chat> chats = chatRepository.findAllByChatRoom_ChatRoomId(chatRoom.getChatRoomId());
           if (chats.isEmpty()) {
               return new ChatRoomResponseDto(chatRoom);
           }
           Chat lastChat = chats.get(chats.size() -1);
           return new ChatRoomResponseDto(chatRoom, lastChat);
        }).collect(Collectors.toList());

//         List<MongoChatRoom> mongoChatRooms = mongoChatRoomRepository.findAllByMembersContains(1L);
//         return mongoChatRooms.stream().map(MongoChatRoomResponseDto::new).collect(Collectors.toList());
    }

    public boolean leaveChatRoom(Employee employee, Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_CHATROOM)
        );

        ChatRoomEmployee chatRoomEmployee = chatRoomEmployeeRepository.findByChatRoomAndEmployee(chatRoom, employee).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_CHATROOM)
        );


        chatRoomEmployee.setDeleted("Y");
        chatRoomEmployeeRepository.save(chatRoomEmployee);
        return true;


//        MongoChatRoom mongoChatRoom = mongoChatRoomRepository.findById(roomId).orElseThrow(
//                () -> new BusinessException(ErrorCode.NOT_FOUND_CHATROOM)
//        );
//
//        List<Employee> members = mongoChatRoom.getMembers();
//        List<Long> membersNo = members.stream().map(Employee::getEmployeeNo).collect(Collectors.toList());
//
//        if (membersNo.contains(employee.getEmployeeNo())) {
//            int index = membersNo.indexOf(employee.getEmployeeNo());
//            members.remove(index);
//            mongoChatRoom.setMembers(members);
//            mongoChatRoomRepository.save(mongoChatRoom);
//            return true;
//        } else {
//            throw new BusinessException(ErrorCode.NOT_FOUND_CHATROOM_MEMBER);
//        }

    }



    @Transactional
    public List<ChatResponseDto> getChatRoomChat(Long roomId) {

        List<Chat> chats = chatRepository.findAllByChatRoom_ChatRoomId(roomId);


        return chats.stream().map(chat -> {
            return new ChatResponseDto(chat, chat.getChatRoom().getChatRoomEmployees().stream().map(ChatRoomEmployee::getEmployee).collect(Collectors.toList()));
        }).collect(Collectors.toList());


//        List<MongoChat> mongoChats =mongoChatRepository.findAllByRoomId(roomId);
//        return mongoChats.stream().map(ChatResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public List<ChatResponseDto> getChatRoomChatOne(Long roomId, String msg) {
        List<Chat> chats = chatRepository.findAllByChatRoom_ChatRoomIdAndMsgContaining(roomId, msg);
        if (chats.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_CHAT);
        }

        return chats.stream().map(chat -> {
            return new ChatResponseDto(chat, chat.getChatRoom().getChatRoomEmployees().stream().map(ChatRoomEmployee::getEmployee).collect(Collectors.toList()));
        }).collect(Collectors.toList());
//       List<MongoChat> mongoChats = mongoChatRepository.findByRoomIdAndMsgContaining(roomId, msg);
//
//       if (mongoChats.isEmpty()) {
//           throw new BusinessException(ErrorCode.NOT_FOUND_CHAT);
//       }
//
//        return mongoChats.stream().map(ChatResponseDto::new).collect(Collectors.toList());
    }
}
