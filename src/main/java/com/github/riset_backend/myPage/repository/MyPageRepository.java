package com.github.riset_backend.myPage.repository;

import com.github.riset_backend.myPage.entity.MyImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageRepository extends JpaRepository<MyImage, Long> {



}
