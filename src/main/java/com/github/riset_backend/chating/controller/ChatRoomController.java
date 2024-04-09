package com.github.riset_backend.chating.controller;

import com.github.riset_backend.chating.dto.TestDto;
import com.github.riset_backend.chating.dto.chatDto.ChatResponseDto;
import com.github.riset_backend.chating.dto.chatRoomDto.ChatRoomResponseDto;
import com.github.riset_backend.chating.dto.chatRoomDto.CreateChatRoomRequestDto;
import com.github.riset_backend.chating.entity.ChatRoom;
import com.github.riset_backend.chating.service.ChatRoomService;
import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@Slf4j
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/room")
    public ResponseEntity<ChatRoomResponseDto> createChatRoom (@RequestBody CreateChatRoomRequestDto dto) {
        ChatRoomResponseDto chatRoom = chatRoomService.createChatRoom(dto);
        return ResponseEntity.ok(chatRoom);
    }

    @GetMapping("/room")
    public ResponseEntity<List<ChatRoomResponseDto>> getChatRooms (@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<ChatRoomResponseDto> chatRoomList = chatRoomService.getChatRoom(customUserDetails.getEmployee().getEmployeeNo());
        return ResponseEntity.ok(chatRoomList);
    }

    @PatchMapping("/room/{roomId}")
    public ResponseEntity<String> leaveChatRoom (@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                 @PathVariable String roomId) {
        chatRoomService.leaveChatRoom(customUserDetails.getEmployee(), roomId);
        return ResponseEntity.ok("채팅방을 나갔습니다.");
    }

    @GetMapping("/room/{roomId}/chat")
    public ResponseEntity<List<ChatResponseDto>> getChatRoomChat (@PathVariable String roomId) {

        List<ChatResponseDto> chats = chatRoomService.getChatRoomChat(roomId);
        return ResponseEntity.ok(chats);
    }

//    @PostMapping("/test")
//    public void test (@RequestBody TestDto testDto) {
//        String[] strings = testDto.getFiles().get(0).split(",");
//        String base64Image = strings[1];
//
//        String extension = "";
//        if (strings[0].equals("data:image/jpeg;base64")) {
//            extension = "jpeg";
//        } else if (strings[0].equals("data:image/png;base64")){
//            extension = "png";
//        } else {
//            extension = "jpg";
//        }
//
//        byte[] imageBytes =  DatatypeConverter.parseBase64Binary(base64Image);
//
//
//        log.info("testDto = {}", strings[1]);
//    }

}
