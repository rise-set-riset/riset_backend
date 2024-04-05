package com.github.riset_backend.login.company.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.riset_backend.global.BaseEntity;
import com.github.riset_backend.schedules.entity.Schedule;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "company")
public class Company extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_no")
    private Long companyNo;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_addr")
    private String companyAddr;

    @Column(name = "company_phone")
    private String companyPhone;

    @Column(name = "zip_code")
    private Integer zipCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    private List<Schedule> companySchedules = new ArrayList<>();



}
