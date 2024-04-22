package com.github.riset_backend.schedules.dto.employee.schedulesALL;


import com.github.riset_backend.login.commute.entity.CommutePlace;

import java.util.List;

public record EmployeeAll(
        Long employeeId,
        String name,
//        String department,
        String position,
        String image,
        String commuteStartTime,
        String commuteEndTime,
        CommutePlace commutePlace,
        EmployeeBooleanResponse booleanResponse,
        List<EmployeeSchedulesResponse> schedulesDetail,
        List<EmployeeHalfLeaveResponse> halfLeaveDetail,
        List<EmployeeAnnualLeaveResponse> annualLeaveDetail
) {
}
