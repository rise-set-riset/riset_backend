package com.github.riset_backend.menu.repository;

import com.github.riset_backend.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
