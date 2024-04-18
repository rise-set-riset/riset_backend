package com.github.riset_backend.login.department.entity;


import com.github.riset_backend.global.BaseEntity;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.department.dto.DepartMentRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "department")
public class Department extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_no")
    private Long deptNo;

    @Column(name = "department_name")
    private String deptName;

    @ManyToOne
    @JoinColumn(name = "company_no", referencedColumnName = "company_no")
    private Company company;

    public Department(DepartMentRequestDto name, Company company) {
        this.deptName = name.name();
        this.company = company;
    }

}
