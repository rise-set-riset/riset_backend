package com.github.riset_backend.chating.controller.mongoChat;

import com.amazonaws.services.s3.AmazonS3;
import com.github.riset_backend.chating.dto.chatDto.ChatResponseDto;
import com.github.riset_backend.chating.dto.chatRoomDto.ChatRoomResponseDto;
import com.github.riset_backend.chating.dto.chatRoomDto.MongoCreateChatRoomRequestDto;
import com.github.riset_backend.chating.dto.chatRoomDto.UpdateChatRoomDto;
import com.github.riset_backend.chating.service.chat.ChatRoomService;
import com.github.riset_backend.global.config.auth.custom.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatRoom")
@Slf4j
public class MongoChatRoomController {

    private final ChatRoomService chatRoomService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;

    @PostMapping
    public ResponseEntity<ChatRoomResponseDto> createChatRoom (@RequestBody MongoCreateChatRoomRequestDto dto) {
        ChatRoomResponseDto chatRoom = chatRoomService.createChatRoom(dto);
        return ResponseEntity.ok(chatRoom);
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomResponseDto>> getChatRooms (@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<ChatRoomResponseDto> chatRoomList = chatRoomService.getChatRoom(customUserDetails.getEmployee());
        return ResponseEntity.ok(chatRoomList);
    }

    @PatchMapping("/{roomId}")
    public ResponseEntity<String> leaveChatRoom (@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                 @PathVariable Long roomId) {
        chatRoomService.leaveChatRoom(customUserDetails.getEmployee(), roomId);
        return ResponseEntity.ok("채팅방을 나갔습니다.");
    }

    @PatchMapping("/roomName/{roomId}")
    public ResponseEntity<ChatRoomResponseDto> updateChatRoomName (@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                   @PathVariable Long roomId,
                                                                   @RequestBody UpdateChatRoomDto dto) {
        ChatRoomResponseDto chatRoomResponseDto = chatRoomService.updateChatRoomName(customUserDetails.getEmployee(), roomId, dto);
        return ResponseEntity.ok(chatRoomResponseDto);
    }

    @GetMapping("/{roomId}/chat")
    public ResponseEntity<List<ChatResponseDto>> getChatRoomChat (@PathVariable Long roomId) {

        List<ChatResponseDto> chats = chatRoomService.getChatRoomChat(roomId);
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/{roomId}/chatOne")
    public ResponseEntity<List<ChatResponseDto>> getChatRoomChatOne (@PathVariable Long roomId, @RequestParam String msg) {
        List<ChatResponseDto> chat = chatRoomService.getChatRoomChatOne(roomId, msg);
        return ResponseEntity.ok(chat);
    }



//    @PostMapping("/test")
//    public String test (@RequestBody TestDto testDto) throws IOException {
//
//        String[] strings = testDto.getFiles().get(0).split(",");
//        String base64Image = strings[1];
//
//
//        String extension = "";
//        if (strings[0].equals("data:image/jpeg;base64")) {
//            extension = "jpeg";
//        } else if (strings[0].equals("data:image/png;base64")){
//            extension = "png";
//        } else if (strings[0].equals("data:image/jpg;base64")) {
//            extension = "jpg";
//        } else if (strings[0].equals("data:application/pdf;base64")) {
//            extension = "pdf";
//        } else if (strings[0].equals("data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64")) {
//            extension = "xlsx";
//        } else if (strings[0].equals("data:application/vnd.openxmlformats-officedocument.wordprocessingml.document;base64")) {
//            extension = "docx";
//        }
//
//        byte[] imageBytes =  DatatypeConverter.parseBase64Binary(base64Image);
//
//        File tempFile = File.createTempFile("file", "." + extension);
//
//        try (OutputStream outputStream = new FileOutputStream(tempFile)) {
//            outputStream.write(imageBytes);
//        }
//
//        String originalName = UUID.randomUUID().toString() + "." + extension;
//
//        amazonS3.putObject(new PutObjectRequest(bucket, originalName, tempFile).withCannedAcl(CannedAccessControlList.PublicRead));
//
//        String awsS3ImageUrl = amazonS3.getUrl(bucket, originalName).toString();
//
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
//            fileOutputStream.close();
//            if (tempFile.delete()) {
//                log.info("File delete success");
//            } else {
//                log.info("File delete fail");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        return awsS3ImageUrl;
//
//    }

}
