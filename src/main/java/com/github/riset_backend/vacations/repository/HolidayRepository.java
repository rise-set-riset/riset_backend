package com.github.riset_backend.vacations.repository;

import com.github.riset_backend.vacations.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
}
