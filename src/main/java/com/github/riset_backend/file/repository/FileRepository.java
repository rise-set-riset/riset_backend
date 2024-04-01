package com.github.riset_backend.file.repository;

import com.github.riset_backend.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
