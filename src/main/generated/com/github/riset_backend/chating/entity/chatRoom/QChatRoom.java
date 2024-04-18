package com.github.riset_backend.chating.entity.chatRoom;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoom is a Querydsl query type for ChatRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoom extends EntityPathBase<ChatRoom> {

    private static final long serialVersionUID = -1227405805L;

    public static final QChatRoom chatRoom = new QChatRoom("chatRoom");

    public final ListPath<com.github.riset_backend.chating.entity.ChatRoomEmployee, com.github.riset_backend.chating.entity.QChatRoomEmployee> chatRoomEmployees = this.<com.github.riset_backend.chating.entity.ChatRoomEmployee, com.github.riset_backend.chating.entity.QChatRoomEmployee>createList("chatRoomEmployees", com.github.riset_backend.chating.entity.ChatRoomEmployee.class, com.github.riset_backend.chating.entity.QChatRoomEmployee.class, PathInits.DIRECT2);

    public final NumberPath<Long> chatRoomId = createNumber("chatRoomId", Long.class);

    public final StringPath chatRoomName = createString("chatRoomName");

    public final DateTimePath<java.time.LocalDateTime> crateAt = createDateTime("crateAt", java.time.LocalDateTime.class);

    public final StringPath deleted = createString("deleted");

    public QChatRoom(String variable) {
        super(ChatRoom.class, forVariable(variable));
    }

    public QChatRoom(Path<? extends ChatRoom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatRoom(PathMetadata metadata) {
        super(ChatRoom.class, metadata);
    }

}

