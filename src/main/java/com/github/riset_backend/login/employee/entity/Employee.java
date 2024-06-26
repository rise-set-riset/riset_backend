package com.github.riset_backend.login.employee.entity;

import com.github.riset_backend.Settlement.entity.Position;
import com.github.riset_backend.login.employee.dto.EmployeeUpdateDto;
import com.github.riset_backend.login.employee.dto.ProfileUpdateDto;
import com.github.riset_backend.manageCompany.dto.Rating;
import com.github.riset_backend.global.BaseEntity;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.department.entity.Department;
import com.github.riset_backend.login.jobGrade.entity.JobGrade;
import com.github.riset_backend.myPage.entity.MyImage;
import com.github.riset_backend.vacations.entity.Holiday;
import com.github.riset_backend.schedules.entity.Schedule;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "employee")
@DynamicUpdate
public class Employee extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_no")
    private Long employeeNo;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "employee_password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "telNumber")
    private String telNumber;

    @Column(name = "employee_name")
    private String name;

    @Column(name = "employee_phone")
    private String phoneNumber;

    @Column(name = "employee_address")
    private String address;

    @Column(name = "employee_birth")
    private String birth;

    @Column(name = "employee_job")
    private String job;

    @Column(name = "total_annual_leave", columnDefinition = "INT DEFAULT 15")
    private Integer totalAnnualLeave;

    @Column(name = "date_of_join")
    private String dateOfJoin;


    @Column(name = "employee_position")
    private String position;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "salary")
    private Integer salary;

    @ManyToOne()
    @JoinColumn(name = "department_no", referencedColumnName = "department_no")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "company_no", referencedColumnName = "company_no")
    private Company company;

    @OneToOne
    @JoinColumn(name = "grade_no", referencedColumnName = "grade_no")
    private JobGrade jobGrade;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role roles;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> employeeScheduleList;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private List<Holiday> holidays;

//    // 반차 여부를 나타내는 필드
//    @Transient
//    private Boolean halfDayLeave;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "my_image_id", referencedColumnName = "image_id") // 외래 키 명시
    private MyImage myImage;

    @Enumerated(EnumType.STRING)
    Rating rating;


    //myPage update
    public void userUpdate(String name, String phoneNumber, String address, String dateOfJoin) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.dateOfJoin = dateOfJoin;

    }


}
