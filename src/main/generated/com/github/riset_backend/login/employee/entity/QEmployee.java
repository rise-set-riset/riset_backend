package com.github.riset_backend.login.employee.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmployee is a Querydsl query type for Employee
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmployee extends EntityPathBase<Employee> {

    private static final long serialVersionUID = -1360772374L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmployee employee = new QEmployee("employee");

    public final com.github.riset_backend.global.QBaseEntity _super = new com.github.riset_backend.global.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final StringPath birth = createString("birth");

    public final com.github.riset_backend.login.company.entity.QCompany company;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    public final com.github.riset_backend.login.department.entity.QDepartment department;

    public final StringPath email = createString("email");

    public final StringPath employeeId = createString("employeeId");

    public final NumberPath<Long> employeeNo = createNumber("employeeNo", Long.class);

    public final ListPath<com.github.riset_backend.schedules.entity.Schedule, com.github.riset_backend.schedules.entity.QSchedule> employeeScheduleList = this.<com.github.riset_backend.schedules.entity.Schedule, com.github.riset_backend.schedules.entity.QSchedule>createList("employeeScheduleList", com.github.riset_backend.schedules.entity.Schedule.class, com.github.riset_backend.schedules.entity.QSchedule.class, PathInits.DIRECT2);

    public final ListPath<com.github.riset_backend.vacations.entity.Holiday, com.github.riset_backend.vacations.entity.QHoliday> holidays = this.<com.github.riset_backend.vacations.entity.Holiday, com.github.riset_backend.vacations.entity.QHoliday>createList("holidays", com.github.riset_backend.vacations.entity.Holiday.class, com.github.riset_backend.vacations.entity.QHoliday.class, PathInits.DIRECT2);

    public final StringPath job = createString("job");

    public final com.github.riset_backend.login.jobGrade.entity.QJobGrade jobGrade;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> localDateTime = _super.localDateTime;

    public final com.github.riset_backend.myPage.entity.QMyImage myImage;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath position = createString("position");

    public final EnumPath<com.github.riset_backend.manageCompany.dto.Rating> rating = createEnum("rating", com.github.riset_backend.manageCompany.dto.Rating.class);

    public final EnumPath<Role> roles = createEnum("roles", Role.class);

    public final StringPath telNumber = createString("telNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final NumberPath<Long> updatedBy = _super.updatedBy;

    public final StringPath zipCode = createString("zipCode");

    public QEmployee(String variable) {
        this(Employee.class, forVariable(variable), INITS);
    }

    public QEmployee(Path<? extends Employee> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmployee(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmployee(PathMetadata metadata, PathInits inits) {
        this(Employee.class, metadata, inits);
    }

    public QEmployee(Class<? extends Employee> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new com.github.riset_backend.login.company.entity.QCompany(forProperty("company")) : null;
        this.department = inits.isInitialized("department") ? new com.github.riset_backend.login.department.entity.QDepartment(forProperty("department"), inits.get("department")) : null;
        this.jobGrade = inits.isInitialized("jobGrade") ? new com.github.riset_backend.login.jobGrade.entity.QJobGrade(forProperty("jobGrade")) : null;
        this.myImage = inits.isInitialized("myImage") ? new com.github.riset_backend.myPage.entity.QMyImage(forProperty("myImage")) : null;
    }

}

