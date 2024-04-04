package com.github.riset_backend.schedules.service;

import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.schedules.entity.Schedule;
import com.github.riset_backend.schedules.dto.CompanyScheduleRequestDto;
import com.github.riset_backend.schedules.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CompanySchedulesService {
    private final ScheduleRepository schedulesRepository;
    private final CompanyRepository companyRepository;


    // 회사 일정 추가
    public void schedulesAdd(CompanyScheduleRequestDto request, Long token) {
        //토큰 밷어서 찾아내는 로직
        Company company = companyRepository.findById(token).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        //회사 일정 추가
        Schedule newSchedule = Schedule.builder()
                .companyNo(company.getCompanyNo())
                .title(request.title())
                .startDate(request.startDate())
                .writer(request.writer())
                .content(request.content())
                .build();

        //List 추가
//        company.getScheduleList().add(newSchedule);

        //변경사항 저장
        companyRepository.save(company);

    }

    // 회사 일정 수정 update
    public void updateSchedules(CompanyScheduleRequestDto request, Long scheduleId) {
        Schedule schedule = schedulesRepository.findById(scheduleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        //회사 일정 수정
        schedule.updateSchedule(request.startDate(), request.endDate(), request.title(), request.content());
        schedulesRepository.save(schedule);
    }



    public void deleteSchedules(Long id) {
        Schedule schedule = schedulesRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        schedulesRepository.delete(schedule);
    }
}
