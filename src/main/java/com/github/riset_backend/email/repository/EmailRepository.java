package com.github.riset_backend.email.repository;

import com.github.riset_backend.email.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {
    Optional<Email> findByCode(String code);



}
