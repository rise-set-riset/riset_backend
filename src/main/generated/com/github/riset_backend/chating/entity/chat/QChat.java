package com.github.riset_backend.chating.entity.chat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChat is a Querydsl query type for Chat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChat extends EntityPathBase<Chat> {

    private static final long serialVersionUID = 1890646963L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChat chat = new QChat("chat");

    public final NumberPath<Long> chatId = createNumber("chatId", Long.class);

    public final com.github.riset_backend.chating.entity.chatRoom.QChatRoom chatRoom;

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final StringPath deleted = createString("deleted");

    public final StringPath filesName = createString("filesName");

    public final StringPath msg = createString("msg");

    public final com.github.riset_backend.login.employee.entity.QEmployee sender;

    public QChat(String variable) {
        this(Chat.class, forVariable(variable), INITS);
    }

    public QChat(Path<? extends Chat> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChat(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChat(PathMetadata metadata, PathInits inits) {
        this(Chat.class, metadata, inits);
    }

    public QChat(Class<? extends Chat> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new com.github.riset_backend.chating.entity.chatRoom.QChatRoom(forProperty("chatRoom")) : null;
        this.sender = inits.isInitialized("sender") ? new com.github.riset_backend.login.employee.entity.QEmployee(forProperty("sender"), inits.get("sender")) : null;
    }

}

