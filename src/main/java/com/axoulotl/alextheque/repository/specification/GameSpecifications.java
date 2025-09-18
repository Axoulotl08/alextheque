package com.axoulotl.alextheque.repository.specification;

import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.model.entity.enums.Status;
import jakarta.persistence.criteria.Join;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class GameSpecifications {

    /**
     * Specification for search by name
     * Game_name LIKE name
     *
     * @param name - The name to search
     * @return the criteria builder
     */
    public static Specification<Game> hasNameLike(String name){
        return(root, query, criteriaBuilder) -> {
            String likePattern = "%" + name.toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), likePattern);
        };
    }

    public static Specification<Game> hasGameId(Integer consoleId){
        return (root, query, criteriaBuilder) -> {
            Join<Game, Console> consoleJoin = root.join("console");
            return criteriaBuilder.equal(consoleJoin.get("id"), consoleId);
        };
    }

    public static Specification<Game> hasStatus(Status status) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Game> hadStartedBefore(LocalDate startedDate){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("startedDate"), startedDate);
    }
}
