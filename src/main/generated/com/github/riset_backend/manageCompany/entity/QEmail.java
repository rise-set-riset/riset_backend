package com.github.riset_backend.manageCompany.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmail is a Querydsl query type for Email
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmail extends EntityPathBase<Email> {

    private static final long serialVersionUID = -1114014381L;

    public static final QEmail email = new QEmail("email");

    public final StringPath code = createString("code");

    public final NumberPath<Long> email_id = createNumber("email_id", Long.class);

    public final NumberPath<Long> employeeId = createNumber("employeeId", Long.class);

    public QEmail(String variable) {
        super(Email.class, forVariable(variable));
    }

    public QEmail(Path<? extends Email> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmail(PathMetadata metadata) {
        super(Email.class, metadata);
    }

}

