package com.github.riset_backend.menu.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMenu is a Querydsl query type for Menu
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenu extends EntityPathBase<Menu> {

    private static final long serialVersionUID = -1214655897L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMenu menu = new QMenu("menu");

    public final com.github.riset_backend.file.entity.QFile file;

    public final StringPath icon = createString("icon");

    public final NumberPath<Long> menuId = createNumber("menuId", Long.class);

    public final NumberPath<Integer> menuOrder = createNumber("menuOrder", Integer.class);

    public final StringPath menuUrl = createString("menuUrl");

    public final QMenu parent;

    public final ListPath<Menu, QMenu> subMenus = this.<Menu, QMenu>createList("subMenus", Menu.class, QMenu.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QMenu(String variable) {
        this(Menu.class, forVariable(variable), INITS);
    }

    public QMenu(Path<? extends Menu> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMenu(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMenu(PathMetadata metadata, PathInits inits) {
        this(Menu.class, metadata, inits);
    }

    public QMenu(Class<? extends Menu> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.file = inits.isInitialized("file") ? new com.github.riset_backend.file.entity.QFile(forProperty("file")) : null;
        this.parent = inits.isInitialized("parent") ? new QMenu(forProperty("parent"), inits.get("parent")) : null;
    }

}

