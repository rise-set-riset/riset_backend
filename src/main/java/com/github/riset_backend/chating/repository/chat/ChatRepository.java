package com.github.riset_backend.chating.repository.chat;

import com.github.riset_backend.chating.entity.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findAllByChatRoom_ChatRoomId(Long chatRoomId);

    List<Chat> findAllByChatRoom_ChatRoomIdAndMsgContaining(Long chatRoomId, String msg);

}
