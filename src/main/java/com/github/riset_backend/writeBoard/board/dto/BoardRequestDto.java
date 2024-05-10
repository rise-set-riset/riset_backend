package com.github.riset_backend.writeBoard.board.dto;

import com.github.riset_backend.file.dto.FileRequestDto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {
    private String title;
    private String content;
}
