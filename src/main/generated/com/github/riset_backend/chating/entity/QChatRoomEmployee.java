package com.github.riset_backend.chating.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoomEmployee is a Querydsl query type for ChatRoomEmployee
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoomEmployee extends EntityPathBase<ChatRoomEmployee> {

    private static final long serialVersionUID = 654017852L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatRoomEmployee chatRoomEmployee = new QChatRoomEmployee("chatRoomEmployee");

    public final com.github.riset_backend.chating.entity.chatRoom.QChatRoom chatRoom;

    public final NumberPath<Long> chatRoomEmployeeId = createNumber("chatRoomEmployeeId", Long.class);

    public final StringPath deleted = createString("deleted");

    public final com.github.riset_backend.login.employee.entity.QEmployee employee;

    public QChatRoomEmployee(String variable) {
        this(ChatRoomEmployee.class, forVariable(variable), INITS);
    }

    public QChatRoomEmployee(Path<? extends ChatRoomEmployee> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatRoomEmployee(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatRoomEmployee(PathMetadata metadata, PathInits inits) {
        this(ChatRoomEmployee.class, metadata, inits);
    }

    public QChatRoomEmployee(Class<? extends ChatRoomEmployee> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new com.github.riset_backend.chating.entity.chatRoom.QChatRoom(forProperty("chatRoom")) : null;
        this.employee = inits.isInitialized("employee") ? new com.github.riset_backend.login.employee.entity.QEmployee(forProperty("employee"), inits.get("employee")) : null;
    }

}

