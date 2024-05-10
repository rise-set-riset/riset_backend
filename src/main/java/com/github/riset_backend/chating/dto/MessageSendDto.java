package com.github.riset_backend.chating.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendDto {
    private String roomId;
    private String msg;
    private List<Long> members;
    private Long sender;
    private List<String> base64File;
}
