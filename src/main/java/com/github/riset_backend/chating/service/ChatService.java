package com.github.riset_backend.chating.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.github.riset_backend.chating.dto.MessageSendDto;
import com.github.riset_backend.chating.dto.chatDto.ChatResponseDto;
import com.github.riset_backend.chating.dto.chatDto.MongoChatResponseDto;
import com.github.riset_backend.chating.entity.chat.Chat;
import com.github.riset_backend.chating.entity.chatRoom.ChatRoom;
import com.github.riset_backend.chating.repository.chat.ChatRepository;
import com.github.riset_backend.chating.repository.chatRoom.ChatRoomRepository;
import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

//    private final MongoChatRepository mongoChatRepository;
//    private final MongoChatRoomRepository mongoChatRoomRepository;
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final EmployeeRepository employeeRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;

    public void sendMessage(MessageSendDto messageSendDto, Long roomId) throws IOException {

        List<String> fileNames = new ArrayList<>();

        if (!messageSendDto.getBase64File().isEmpty()) {
            messageSendDto.getBase64File().forEach(
                    base64 -> {
                        try {
                            chatFileUpload(base64, fileNames);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        }

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(
                () -> new BusinessException(ErrorCode.DIFFERENT_CHATROOM)
        );

        Employee sender = employeeRepository.findByEmployeeNo(messageSendDto.getSender()).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_EMPLOYEE)
        );

        List<Employee> members = new ArrayList<>();

        messageSendDto.getMembers().forEach(employeeNo -> {
            Employee member = employeeRepository.findByEmployeeNo(messageSendDto.getSender()).orElseThrow(
                    () -> new BusinessException(ErrorCode.NOT_FOUND_EMPLOYEE)
            );

            members.add(member);
        });


        LocalDateTime now =  new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        String fileName = fileNames.isEmpty() ? "null" : fileNames.get(0);


        Chat chat = new Chat(chatRoom, messageSendDto, now, sender, fileName);
        Chat newChat = chatRepository.save(chat);

        log.info("여기까지 실행!!!!!!!");

        messagingTemplate.convertAndSend("/sub/chat/message/" + roomId, new ChatResponseDto(newChat, members));


        //        mongoChatRoomRepository.findById(mongoMessageSendDto.getRoomId()).orElseThrow(
//                () -> new BusinessException(ErrorCode.NOT_FOUND_CHATROOM)
//        );
//
//        if(!Objects.equals(messageSendDto.getRoomId(), roomId)) {
//            throw  new BusinessException(ErrorCode.DIFFERENT_CHATROOM);
//        }


//        MongoChat mongoChat = new MongoChat(mongoMessageSendDto, now, sender);
//        mongoChatRepository.save(mongoChat);
    }

    private void chatFileUpload(String base64, List<String> fileNames) throws IOException {
        String[] strings = base64.split(",");
        String base64Image = strings[1];

        String extension = "";
        if (strings[0].equals("data:image/jpeg;base64")) {
            extension = "jpeg";
        } else if (strings[0].equals("data:image/png;base64")){
            extension = "png";
        } else if (strings[0].equals("data:image/jpg;base64")) {
            extension = "jpg";
        } else if (strings[0].equals("data:application/pdf;base64")) {
            extension = "pdf";
        } else if (strings[0].equals("data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64")) {
            extension = "xlsx";
        } else if (strings[0].equals("data:application/vnd.openxmlformats-officedocument.wordprocessingml.document;base64")) {
            extension = "docx";
        }

        byte[] imageBytes =  DatatypeConverter.parseBase64Binary(base64Image);

        File tempFile = File.createTempFile("file", "." + extension);

        try (OutputStream outputStream = new FileOutputStream(tempFile)) {
            outputStream.write(imageBytes);
        }

        String originalName = UUID.randomUUID().toString() + "." + extension;

        amazonS3.putObject(new PutObjectRequest(bucket, originalName, tempFile).withCannedAcl(CannedAccessControlList.PublicRead));

        String awsS3ImageUrl = amazonS3.getUrl(bucket, originalName).toString();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            fileOutputStream.close();
            if (tempFile.delete()) {
                log.info("File delete success");
            } else {
                log.info("File delete fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        fileNames.add(awsS3ImageUrl);
    }
}
