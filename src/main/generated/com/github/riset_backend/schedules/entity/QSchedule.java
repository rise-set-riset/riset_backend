package com.github.riset_backend.schedules.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSchedule is a Querydsl query type for Schedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchedule extends EntityPathBase<Schedule> {

    private static final long serialVersionUID = 2010257636L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSchedule schedule = new QSchedule("schedule");

    public final StringPath color = createString("color");

    public final com.github.riset_backend.login.company.entity.QCompany company;

    public final StringPath content = createString("content");

    public final com.github.riset_backend.login.employee.entity.QEmployee employee;

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> scheduleNo = createNumber("scheduleNo", Long.class);

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public final EnumPath<com.github.riset_backend.vacations.dto.Status> status = createEnum("status", com.github.riset_backend.vacations.dto.Status.class);

    public final StringPath title = createString("title");

    public final StringPath writer = createString("writer");

    public QSchedule(String variable) {
        this(Schedule.class, forVariable(variable), INITS);
    }

    public QSchedule(Path<? extends Schedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSchedule(PathMetadata metadata, PathInits inits) {
        this(Schedule.class, metadata, inits);
    }

    public QSchedule(Class<? extends Schedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new com.github.riset_backend.login.company.entity.QCompany(forProperty("company")) : null;
        this.employee = inits.isInitialized("employee") ? new com.github.riset_backend.login.employee.entity.QEmployee(forProperty("employee"), inits.get("employee")) : null;
    }

}

