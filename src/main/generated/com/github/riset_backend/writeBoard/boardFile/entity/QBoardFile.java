package com.github.riset_backend.writeBoard.boardFile.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardFile is a Querydsl query type for BoardFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardFile extends EntityPathBase<BoardFile> {

    private static final long serialVersionUID = -1672719426L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardFile boardFile = new QBoardFile("boardFile");

    public final com.github.riset_backend.writeBoard.board.entity.QBoard board;

    public final NumberPath<Long> boardFileId = createNumber("boardFileId", Long.class);

    public final com.github.riset_backend.file.entity.QFile file;

    public QBoardFile(String variable) {
        this(BoardFile.class, forVariable(variable), INITS);
    }

    public QBoardFile(Path<? extends BoardFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardFile(PathMetadata metadata, PathInits inits) {
        this(BoardFile.class, metadata, inits);
    }

    public QBoardFile(Class<? extends BoardFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new com.github.riset_backend.writeBoard.board.entity.QBoard(forProperty("board"), inits.get("board")) : null;
        this.file = inits.isInitialized("file") ? new com.github.riset_backend.file.entity.QFile(forProperty("file")) : null;
    }

}

