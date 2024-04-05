package com.github.riset_backend.schedules.repository;


import com.github.riset_backend.schedules.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {


}
