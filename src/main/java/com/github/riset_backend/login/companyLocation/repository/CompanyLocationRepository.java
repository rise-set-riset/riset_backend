package com.github.riset_backend.login.companyLocation.repository;

import com.github.riset_backend.login.companyLocation.entity.CompanyLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyLocationRepository extends JpaRepository<CompanyLocation, Long> {
}
