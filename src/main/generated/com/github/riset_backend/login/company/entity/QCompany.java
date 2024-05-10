package com.github.riset_backend.login.company.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompany is a Querydsl query type for Company
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompany extends EntityPathBase<Company> {

    private static final long serialVersionUID = 1640116436L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompany company = new QCompany("company");

    public final com.github.riset_backend.global.QBaseEntity _super = new com.github.riset_backend.global.QBaseEntity(this);

    public final StringPath companyAddr = createString("companyAddr");

    public final StringPath companyName = createString("companyName");

    public final NumberPath<Long> companyNo = createNumber("companyNo", Long.class);

    public final StringPath companyPhone = createString("companyPhone");

    public final ListPath<com.github.riset_backend.schedules.entity.Schedule, com.github.riset_backend.schedules.entity.QSchedule> companySchedules = this.<com.github.riset_backend.schedules.entity.Schedule, com.github.riset_backend.schedules.entity.QSchedule>createList("companySchedules", com.github.riset_backend.schedules.entity.Schedule.class, com.github.riset_backend.schedules.entity.QSchedule.class, PathInits.DIRECT2);

    public final StringPath companyTelNo = createString("companyTelNo");

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> localDateTime = _super.localDateTime;

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final StringPath Mdfr_Id = createString("Mdfr_Id");

    public final com.github.riset_backend.myPage.entity.QMyImage myImage;

    public final NumberPath<Integer> parentCompanyNo = createNumber("parentCompanyNo", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final NumberPath<Long> updatedBy = _super.updatedBy;

    public final NumberPath<Integer> zipCode = createNumber("zipCode", Integer.class);

    public QCompany(String variable) {
        this(Company.class, forVariable(variable), INITS);
    }

    public QCompany(Path<? extends Company> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompany(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompany(PathMetadata metadata, PathInits inits) {
        this(Company.class, metadata, inits);
    }

    public QCompany(Class<? extends Company> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.myImage = inits.isInitialized("myImage") ? new com.github.riset_backend.myPage.entity.QMyImage(forProperty("myImage")) : null;
    }

}

