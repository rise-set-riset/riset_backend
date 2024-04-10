package com.github.riset_backend.chating.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.github.riset_backend.chating.dto.chatDto.ChatResponseDto;
import com.github.riset_backend.chating.dto.MessageSendDto;
import com.github.riset_backend.chating.entity.Chat;
import com.github.riset_backend.chating.repository.ChatRepository;
import com.github.riset_backend.chating.repository.ChatRoomRepository;
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

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final EmployeeRepository employeeRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;

    public void sendMessage(MessageSendDto messageSendDto, String roomId) throws IOException {

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
