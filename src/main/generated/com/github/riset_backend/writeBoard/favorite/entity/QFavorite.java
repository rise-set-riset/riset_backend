package com.github.riset_backend.writeBoard.favorite.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavorite is a Querydsl query type for Favorite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavorite extends EntityPathBase<Favorite> {

    private static final long serialVersionUID = -2080804464L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavorite favorite = new QFavorite("favorite");

    public final com.github.riset_backend.writeBoard.board.entity.QBoard board;

    public final com.github.riset_backend.login.employee.entity.QEmployee employee;

    public final NumberPath<Long> favoriteId = createNumber("favoriteId", Long.class);

    public final NumberPath<Integer> indexNumber = createNumber("indexNumber", Integer.class);

    public QFavorite(String variable) {
        this(Favorite.class, forVariable(variable), INITS);
    }

    public QFavorite(Path<? extends Favorite> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavorite(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavorite(PathMetadata metadata, PathInits inits) {
        this(Favorite.class, metadata, inits);
    }

    public QFavorite(Class<? extends Favorite> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new com.github.riset_backend.writeBoard.board.entity.QBoard(forProperty("board"), inits.get("board")) : null;
        this.employee = inits.isInitialized("employee") ? new com.github.riset_backend.login.employee.entity.QEmployee(forProperty("employee"), inits.get("employee")) : null;
    }

}

