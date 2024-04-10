package com.github.riset_backend.myPage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
public class MyImage {
    @Id
    @Column(name = "myImageId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myImageId;
    private String fileName;
    private String filePath;

    @Builder
    public MyImage(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public void updateImage(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }
}