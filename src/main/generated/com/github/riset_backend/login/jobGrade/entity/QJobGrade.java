package com.github.riset_backend.login.jobGrade.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.github.riset_backend.manageCompany.dto.Rating;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QJobGrade is a Querydsl query type for JobGrade
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJobGrade extends EntityPathBase<JobGrade> {

    private static final long serialVersionUID = 58991042L;

    public static final QJobGrade jobGrade = new QJobGrade("jobGrade");

    public final com.github.riset_backend.global.QBaseEntity _super = new com.github.riset_backend.global.QBaseEntity(this);

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    public final NumberPath<Integer> grade = createNumber("grade", Integer.class);

    public final NumberPath<Long> gradeNo = createNumber("gradeNo", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> localDateTime = _super.localDateTime;

    public final EnumPath<Rating> rating = createEnum("rating", Rating.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final NumberPath<Long> updatedBy = _super.updatedBy;

    public QJobGrade(String variable) {
        super(JobGrade.class, forVariable(variable));
    }

    public QJobGrade(Path<? extends JobGrade> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJobGrade(PathMetadata metadata) {
        super(JobGrade.class, metadata);
    }

}

