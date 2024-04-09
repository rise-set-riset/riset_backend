package com.github.riset_backend.chating.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private List<String> base64File;
}
