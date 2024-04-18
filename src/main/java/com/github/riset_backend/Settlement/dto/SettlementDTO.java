package com.github.riset_backend.Settlement.dto;

import com.github.riset_backend.manageCompany.dto.Rating;

/**
 *
 *
 * @param name                     직원 이름
 * @param Rating                   직책에 따른 급여
 * @param totalSalary              총 급여
 * @param nationalPension          국민연금
 * @param healthInsurance          건강보험
 * @param employmentInsurance      고용보험
 * @param industrialAccidentInsurance 산재보험
 * @param preTax                   세전 급여
 * @param postTax                  세후 급여
 * @param severancePay             퇴직금
 */

public record SettlementDTO(
        String name,   // 이름
        com.github.riset_backend.manageCompany.dto.Rating Rating, // 직책
        double totalSalary,
        double healthInsurance, //건강보험
        double nationalPension, //국민연금
        double employmentInsurance, //고용보험
        double industrialAccidentInsurance, //산재보험
        double preTax, //세전
        double postTax, //세후
        double severancePay //퇴직금


) {
}

