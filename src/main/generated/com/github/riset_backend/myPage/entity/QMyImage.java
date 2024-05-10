package com.github.riset_backend.myPage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMyImage is a Querydsl query type for MyImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyImage extends EntityPathBase<MyImage> {

    private static final long serialVersionUID = -1714024565L;

    public static final QMyImage myImage = new QMyImage("myImage");

    public final StringPath fileName = createString("fileName");

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Long> myImageId = createNumber("myImageId", Long.class);

    public QMyImage(String variable) {
        super(MyImage.class, forVariable(variable));
    }

    public QMyImage(Path<? extends MyImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMyImage(PathMetadata metadata) {
        super(MyImage.class, metadata);
    }

}

