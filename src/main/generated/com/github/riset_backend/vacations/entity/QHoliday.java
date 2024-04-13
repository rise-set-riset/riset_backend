package com.github.riset_backend.vacations.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHoliday is a Querydsl query type for Holiday
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHoliday extends EntityPathBase<Holiday> {

    private static final long serialVersionUID = 1218013361L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHoliday holiday = new QHoliday("holiday");

    public final StringPath comment = createString("comment");

    public final com.github.riset_backend.login.employee.entity.QEmployee employee;

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> leaveNo = createNumber("leaveNo", Long.class);

    public final EnumPath<com.github.riset_backend.vacations.dto.LeaveType> leaveStatus = createEnum("leaveStatus", com.github.riset_backend.vacations.dto.LeaveType.class);

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public final EnumPath<com.github.riset_backend.vacations.dto.Status> status = createEnum("status", com.github.riset_backend.vacations.dto.Status.class);

    public final NumberPath<Long> vacationsDay = createNumber("vacationsDay", Long.class);

    public QHoliday(String variable) {
        this(Holiday.class, forVariable(variable), INITS);
    }

    public QHoliday(Path<? extends Holiday> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHoliday(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHoliday(PathMetadata metadata, PathInits inits) {
        this(Holiday.class, metadata, inits);
    }

    public QHoliday(Class<? extends Holiday> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new com.github.riset_backend.login.employee.entity.QEmployee(forProperty("employee"), inits.get("employee")) : null;
    }

}

