package com.github.riset_backend.login.commute.repository;

import com.github.riset_backend.login.commute.entity.Commute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommuteRepository extends JpaRepository<Commute, Long> {
}
