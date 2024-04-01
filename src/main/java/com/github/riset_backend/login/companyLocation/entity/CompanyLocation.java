package com.github.riset_backend.login.companyLocation.entity;


import com.github.riset_backend.global.BaseEntity;
import com.github.riset_backend.login.company.entity.Company;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "company_location")
public class CompanyLocation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_no")
    private Long locationNo;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "commnet")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "company_no", referencedColumnName = "company_no")
    private Company company;
}
