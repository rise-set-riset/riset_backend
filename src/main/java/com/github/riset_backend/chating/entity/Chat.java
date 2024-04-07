package com.github.riset_backend.chating.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "chat")
public class Chat {
    @Id
    private String id;
    private String msg;
    private String sender;
    private String receiver;
    private Integer roomNum;
}
