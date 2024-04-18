package com.github.riset_backend.login.commute.repository;

import com.github.riset_backend.login.commute.entity.Commute;
import com.github.riset_backend.login.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommuteRepository extends JpaRepository<Commute, Long> {
    @Query("SELECT c FROM Commute c WHERE YEAR(c.commuteDate) = :year AND MONTH(c.commuteDate) = :month AND c.employee = :employee")
    List<Commute> findByYearAndMonthAndEmployee(int year, int month, Employee employee);

    @Query("SELECT c FROM Commute c WHERE c.commuteDate = :date AND c.employee = :employee")
    Commute findByDateAndEmployee(LocalDate date, Employee employee);

    Optional<Commute> findTopByEmployeeOrderByCommuteDateDesc(Employee emp);

    Optional<Commute> findByEmployeeAndCommuteDate(Employee employee, LocalDate today);

    List<Commute> findByEmployeeAndCommuteDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);

}
