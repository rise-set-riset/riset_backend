package com.github.riset_backend.file.dto;

import com.github.riset_backend.file.entity.File;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class FileResponseDto {
    private String fileName;
    private Long fileSize;
    private String fileType;
    private String fileUrl;

    public FileResponseDto(File file) {
        this.fileName = file.getFileName();
        this.fileSize = file.getFileSize();
        this.fileType = file.getFileType();
        this.fileUrl = file.getFilePath();
    }
}
