package com.axoulotl.alextheque.repository;

import com.axoulotl.alextheque.model.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    Page<Game> findAllByOrderByNameDesc(Pageable pageable);
}