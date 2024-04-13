package com.github.riset_backend.vacations.repository;

import com.github.riset_backend.login.employee.entity.Employee;
import com.github.riset_backend.vacations.entity.Holiday;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    List<Holiday> findAllByEmployee(Employee employee);

    @Query("SELECT h FROM Holiday h WHERE YEAR(h.startDate) = :year AND MONTH(h.startDate) = :month AND h.employee = :employee")
    List<Holiday> findByYearAndMonthAndEmployee(@Param("year") int year, @Param("month") int month, @Param("employee") Employee employee);
}
