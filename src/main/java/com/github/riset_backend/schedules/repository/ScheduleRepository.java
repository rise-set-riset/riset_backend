package com.github.riset_backend.schedules.repository;


import com.github.riset_backend.schedules.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
