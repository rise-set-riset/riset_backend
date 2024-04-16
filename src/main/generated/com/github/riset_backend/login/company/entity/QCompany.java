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

    public final NumberPath<Integer> parentCompanyNo = createNumber("parentCompanyNo", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final NumberPath<Long> updatedBy = _super.updatedBy;

    public final NumberPath<Integer> zipCode = createNumber("zipCode", Integer.class);

    public QCompany(String variable) {
        super(Company.class, forVariable(variable));
    }

    public QCompany(Path<? extends Company> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompany(PathMetadata metadata) {
        super(Company.class, metadata);
    }

}

