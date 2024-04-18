package com.github.riset_backend.login.company.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.riset_backend.global.BaseEntity;
import com.github.riset_backend.myPage.entity.MyImage;
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

    @Column(name = "parent_company_no")
    private Integer parentCompanyNo;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_addr")
    private String companyAddr;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "company_phone")
    private String companyPhone;

    @Column(name = "zip_code")
    private Integer zipCode;

    @Column(name = "company_tel_no")
    private String companyTelNo;

    @Column(name = "mdfr_id")
    private String Mdfr_Id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> companySchedules = new ArrayList<>();

    //조직 이미지
    @Setter
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "my_image_id", referencedColumnName = "image_id") // 외래 키 명시
    private MyImage myImage;

    public Company(Double latitude, Double longitude, String companyName, String companyAddr) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.companyName = companyName;
        this.companyAddr = companyAddr;
    }
}
