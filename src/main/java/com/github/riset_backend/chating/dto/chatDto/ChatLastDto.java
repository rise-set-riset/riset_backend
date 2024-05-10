package com.github.riset_backend.chating.dto.chatDto;

import com.github.riset_backend.chating.entity.chat.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatLastDto {

    private Long chatId;
    private String msg;
    private String fileNames;

    public ChatLastDto(Chat chat) {
        this.chatId = chat.getChatId();
        this.msg = chat.getMsg();
        this.fileNames = chat.getFilesName();
    }
}
