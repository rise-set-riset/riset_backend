package com.github.riset_backend.Settlement.dto;

import com.github.riset_backend.Settlement.dto.SettlementDTO;
import com.github.riset_backend.login.employee.entity.Employee;
import org.springframework.data.domain.Page;
import lombok.Getter;

@Getter
public class SettlementServiceResponse {
    private final Page<SettlementDTO> settlements;
    private final int totalPages;
    private final long totalElements;
    private final int currentPageNumber;
    private final int pageSize;

    public SettlementServiceResponse(Page<SettlementDTO> settlements, Page<Employee> employees) {
        this.settlements = settlements;
        this.totalPages = settlements.getTotalPages();
        this.totalElements = settlements.getTotalElements();
        this.currentPageNumber = settlements.getNumber();
        this.pageSize = settlements.getSize();
    }
}
