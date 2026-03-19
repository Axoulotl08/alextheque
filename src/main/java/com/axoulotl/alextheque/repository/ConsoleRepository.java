package com.axoulotl.alextheque.repository;

import com.axoulotl.alextheque.model.entity.Console;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsoleRepository extends JpaRepository<Console, Integer> {
    @EntityGraph(attributePaths = {"gameList"})
    Optional<Console> findById(Integer id);
}
