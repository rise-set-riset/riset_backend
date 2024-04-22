package com.github.riset_backend.global;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

//jpa 제공하는 감시 기능을 활용한 리스터, 생성일시, 수정일시, 생성자, 수정자 와 같은 감시정보를 자동으로 처리
//상속만 하면 쓸 수 있게 해줌 , 부모에게 선언
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime localDateTime;

    @LastModifiedBy
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    // 확실하진 않지만.. 아래 두개는 많이 사용 안하는거 같더라구요..? 필요없으면 지우셔도 됩니다!
    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private Long updatedBy;

}
