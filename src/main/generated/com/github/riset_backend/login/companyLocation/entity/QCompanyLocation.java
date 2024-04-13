package com.github.riset_backend.login.companyLocation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompanyLocation is a Querydsl query type for CompanyLocation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompanyLocation extends EntityPathBase<CompanyLocation> {

    private static final long serialVersionUID = -2140960460L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompanyLocation companyLocation = new QCompanyLocation("companyLocation");

    public final com.github.riset_backend.global.QBaseEntity _super = new com.github.riset_backend.global.QBaseEntity(this);

    public final StringPath comment = createString("comment");

    public final com.github.riset_backend.login.company.entity.QCompany company;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> localDateTime = _super.localDateTime;

    public final NumberPath<Long> locationNo = createNumber("locationNo", Long.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final NumberPath<Long> updatedBy = _super.updatedBy;

    public QCompanyLocation(String variable) {
        this(CompanyLocation.class, forVariable(variable), INITS);
    }

    public QCompanyLocation(Path<? extends CompanyLocation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompanyLocation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompanyLocation(PathMetadata metadata, PathInits inits) {
        this(CompanyLocation.class, metadata, inits);
    }

    public QCompanyLocation(Class<? extends CompanyLocation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new com.github.riset_backend.login.company.entity.QCompany(forProperty("company")) : null;
    }

}

