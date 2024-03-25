package com.github.riset_backend.global.config.swagger;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}


// JPA 감시 기능이에요!
// 엔티티의 생성일(createdDate) 및 수정일(modifiedDate)을 자동으로 관리하고 갱신하는 기능입다
