package com.github.riset_backend.chating.dto;

import jakarta.mail.Multipart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageSendDto {
    private String roomId;
    private String msg;
    private List<Long> employeesNo;
    private Long sender;
}
