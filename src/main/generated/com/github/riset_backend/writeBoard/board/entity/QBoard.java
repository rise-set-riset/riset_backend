package com.github.riset_backend.writeBoard.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -238548546L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final ListPath<com.github.riset_backend.writeBoard.boardFile.entity.BoardFile, com.github.riset_backend.writeBoard.boardFile.entity.QBoardFile> boardFiles = this.<com.github.riset_backend.writeBoard.boardFile.entity.BoardFile, com.github.riset_backend.writeBoard.boardFile.entity.QBoardFile>createList("boardFiles", com.github.riset_backend.writeBoard.boardFile.entity.BoardFile.class, com.github.riset_backend.writeBoard.boardFile.entity.QBoardFile.class, PathInits.DIRECT2);

    public final NumberPath<Long> boardNo = createNumber("boardNo", Long.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final StringPath deleted = createString("deleted");

    public final com.github.riset_backend.login.employee.entity.QEmployee employee;

    public final ListPath<com.github.riset_backend.writeBoard.reply.entity.Reply, com.github.riset_backend.writeBoard.reply.entity.QReply> replies = this.<com.github.riset_backend.writeBoard.reply.entity.Reply, com.github.riset_backend.writeBoard.reply.entity.QReply>createList("replies", com.github.riset_backend.writeBoard.reply.entity.Reply.class, com.github.riset_backend.writeBoard.reply.entity.QReply.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new com.github.riset_backend.login.employee.entity.QEmployee(forProperty("employee"), inits.get("employee")) : null;
    }

}

