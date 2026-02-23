package com.axoulotl.alextheque.repository;

import com.axoulotl.alextheque.model.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer>, JpaSpecificationExecutor<Game> {
    Page<Game> findAllByOrderByNameDesc(Pageable pageable);

    @EntityGraph(attributePaths = {"console"})
    Optional<Game> findById(Integer id);
}