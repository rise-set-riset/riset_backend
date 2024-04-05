package com.github.riset_backend.schedules.service;

import com.github.riset_backend.global.config.exception.BusinessException;
import com.github.riset_backend.global.config.exception.ErrorCode;
import com.github.riset_backend.login.company.entity.Company;
import com.github.riset_backend.login.company.repository.CompanyRepository;
import com.github.riset_backend.schedules.dto.CompanyScheduleResponseDto;
import com.github.riset_backend.schedules.dto.UpdateComScheduleDto;
import com.github.riset_backend.schedules.entity.Schedule;
import com.github.riset_backend.schedules.dto.CompanyScheduleRequestDto;
import com.github.riset_backend.schedules.repository.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanySchedulesService {
    private final ScheduleRepository schedulesRepository;
    private final CompanyRepository companyRepository;


    // 회사 일정 추가
    @Transactional
    public void schedulesAdd(CompanyScheduleRequestDto request) {

        //todo : 회사 정보를 받아야하고 하드코딩 된 부분 수정해야합니다.
        //todo : jwt 파싱  회사정보

        Company company = companyRepository.findById(3L).orElseThrow(() -> new BusinessException(ErrorCode.NOT_ADMIN, "회사가 없네요"));

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
    }

    @Transactional
    public Schedule updateComSchedule(UpdateComScheduleDto request) {
        //todo : token 유효성 확인 해야합니다
        //todo : 에러 상태코드 수정 해야합니다
        Schedule schedule = schedulesRepository.findById(request.ScheduleId()).orElseThrow(() -> new BusinessException(ErrorCode.NO_BUY_ORDER, "없어!!"));

        if (schedule == null) {
            throw new BusinessException(ErrorCode.NO_BUY_ORDER, "일정을 찾을 수 없습니다.");
        }
        schedule.update(
                request.title(),
                request.content(),
                request.startDate(),
                request.endDate(),
                request.writer()
        );
        Schedule saveSchedule = schedulesRepository.save(schedule);


        return saveSchedule;
    }


    // 회사의 해당하는 월의 값을 받아서 전부 return / 회사일정 시 달력의 값에 넣어주기 위해서
    public Map<String, List<CompanyScheduleResponseDto>> getAllCompanySchedules(String currentMonth) {
        Company company = companyRepository.findById(3L).orElseThrow(() -> new BusinessException(ErrorCode.NOT_ADMIN, "회사가 없네요"));
        return company.getCompanySchedules().stream()
                .filter(schedule -> schedule.getStartDate().getMonthValue() == Long.parseLong(currentMonth))
                .sorted(Comparator.comparing(Schedule::getStartDate))
                .map(schedule -> new CompanyScheduleResponseDto(
                        schedule.getScheduleNo(),
                        schedule.getWriter(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getStartDate().toString(),
                        schedule.getEndDate().toString()
                ))
                .collect(Collectors.groupingBy(dto -> String.format("%02d일", LocalDateTime.parse(dto.startDate()).getDayOfMonth()), TreeMap::new, Collectors.toList()));
    }

    //회사 일정 수정
    public String deleteCompanySchedule(Long scheduleId, String token) {
        //todo : 토큰의 회사 유저 확인해야합니다.



        Optional<Schedule> scheduleOptional = schedulesRepository.findById(scheduleId);

        if (scheduleOptional.isPresent()) {
            Schedule schedule = scheduleOptional.get();
            schedulesRepository.delete(schedule);
            return "일정이 삭제되었습니다";
        } else {
            return "없는 일정 정보입니다";
        }
    }

}
