package com.github.riset_backend.writeBoard.favorite.repository;

import com.github.riset_backend.writeBoard.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
