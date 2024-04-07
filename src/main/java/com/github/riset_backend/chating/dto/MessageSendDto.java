package com.github.riset_backend.chating.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageSendDto {
    private String msg;
    private Integer roomNum;
}
