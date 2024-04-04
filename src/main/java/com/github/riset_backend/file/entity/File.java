package com.github.riset_backend.file.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor()
@Entity
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long fileId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_type")
    private String fileType;

    public File(String fileName, String filePath, Long fileSize, String fileType) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.fileType = fileType;
    }

    private File buildFile (String fileName, String filePath, Long fileSize, String fileType) {
        return File.builder()
                .fileName(fileName)
                .filePath(filePath)
                .fileSize(fileSize)
                .fileType(fileType)
                .build();
    }

}
