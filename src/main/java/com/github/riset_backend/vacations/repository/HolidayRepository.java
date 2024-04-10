package com.github.riset_backend.vacations.repository;

import com.github.riset_backend.vacations.dto.Status;
import com.github.riset_backend.vacations.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    List<Holiday> findByStatus(Status status);
}
