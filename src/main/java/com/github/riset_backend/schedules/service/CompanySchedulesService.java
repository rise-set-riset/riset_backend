package com.github.riset_backend.schedules.service;

import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.schedules.dto.CompanyScheduleResponseDto;
import com.github.riset_backend.schedules.entity.Schedule;
import com.github.riset_backend.schedules.dto.CompanyScheduleRequestDto;
import com.github.riset_backend.schedules.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@RequiredArgsConstructor
public class CompanySchedulesService {
    private final ScheduleRepository schedulesRepository;
    private final CompanyRepository companyRepository;


    // 회사 일정 추가

    public void schedulesAdd(CompanyScheduleRequestDto request) {

        Company company = companyRepository.findById(3L).orElseThrow(() -> new BusinessException(ErrorCode.NOT_ADMIN, "회사가 없네요"));

        System.out.println(company);


        Schedule schedule = Schedule.builder()
                .company(company)
                .startDate(request.startDate())
                .endDate(request.endDate())
                .content(request.content())
                .title(request.title())
                .writer(request.writer())
                .build();


        company.getCompanySchedules().add(schedule);
        companyRepository.save(company);
        schedulesRepository.save(schedule);

    }
    @Transactional
    public Map<String, Map<String, List<CompanyScheduleResponseDto>>> getAllCompanySchedules(Long currentMonth) {
        Company company = companyRepository.findById(3L).orElseThrow(() -> new BusinessException(ErrorCode.NOT_ADMIN, "회사가 없네요"));

        List<Schedule> allSchedules = company.getCompanySchedules();
        Map<String, Map<String, List<CompanyScheduleResponseDto>>> schedulesMap = new TreeMap<>();

        // 스케줄을 시작일 기준으로 정렬
        allSchedules.sort(Comparator.comparing(Schedule::getStartDate));

        for (Schedule schedule : allSchedules) {
            LocalDate startDate = schedule.getStartDate().toLocalDate();
            String dayOfMonth = startDate.format(DateTimeFormatter.ofPattern("d일"));

            if (startDate.getMonthValue() == currentMonth.intValue()) {
                String key = String.valueOf(currentMonth);
                schedulesMap.putIfAbsent(key, new TreeMap<>());

                Map<String, List<CompanyScheduleResponseDto>> monthMap = schedulesMap.get(key);
                monthMap.putIfAbsent(dayOfMonth, new ArrayList<>());

                List<CompanyScheduleResponseDto> daySchedules = monthMap.get(dayOfMonth);
                daySchedules.add(new CompanyScheduleResponseDto(
                        schedule.getScheduleNo(),
                        schedule.getWriter(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        schedule.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                ));
            }
        }

        return schedulesMap;
    }



}
