package com.github.riset_backend.Settlement.service;

import com.github.riset_backend.Settlement.dto.Rating;
import com.github.riset_backend.Settlement.dto.SettlementDTO;
import com.github.riset_backend.Settlement.dto.SettlementServiceResponse;
import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.login.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final EmployeeRepository employeeRepository;

    private final static double HEALTH_INSURANCE_RATE = 0.0335;
    private final static double NATIONAL_PENSION_RATE = 0.045;
    private final static double EMPLOYMENT_INSURANCE_RATE = 0.008;
    private final static double INDUSTRIAL_ACCIDENT_INSURANCE_RATE = 0.001;

    public SettlementServiceResponse getSettlementService(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Employee> employees = employeeRepository.findAll(pageable);

        Page<SettlementDTO> settlements = employees.map(employee -> {
            Rating rating = employee.getRating();
            int salary = rating.getSalary();
            double healthInsurance = salary * HEALTH_INSURANCE_RATE;
            double nationalPension = salary * NATIONAL_PENSION_RATE;
            double employmentInsurance = salary * EMPLOYMENT_INSURANCE_RATE;
            double industrialAccidentInsurance = salary * INDUSTRIAL_ACCIDENT_INSURANCE_RATE;
            double totalSalary = salary + healthInsurance + nationalPension + employmentInsurance + industrialAccidentInsurance;

            return new SettlementDTO(
                    employee.getName(),
                    rating,
                    totalSalary,
                    healthInsurance,
                    nationalPension,
                    employmentInsurance,
                    industrialAccidentInsurance,
                    salary,
                    totalSalary - healthInsurance - nationalPension - employmentInsurance - industrialAccidentInsurance,
                    0 // 퇴직금
            );
        });

        return new SettlementServiceResponse(settlements, employees);
    }

}
