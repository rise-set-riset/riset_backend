package com.github.riset_backend.schedules.service;

import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;

import com.github.riset_backend.schedules.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeScheduleService {

    private final ScheduleRepository schedulesRepository;
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;







}
