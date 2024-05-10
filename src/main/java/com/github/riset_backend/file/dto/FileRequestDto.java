package com.github.riset_backend.file.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@Builder
public class FileRequestDto {

    private String fileName;
    private BigInteger fileSize;
    private String fileType;
    private String fileUrl;

}
