package com.axoulotl.alextheque.repository.specification;

import com.axoulotl.alextheque.model.dto.input.SearchGameDTO;
import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.model.entity.enums.Status;
import jakarta.persistence.criteria.Join;
import org.apache.commons.lang3.StringUtils;
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
    private static Specification<Game> hasNameLike(String name){
        return(root, query, criteriaBuilder) -> {
            String likePattern = "%" + name.toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), likePattern);
        };
    }

    private static Specification<Game> hasGameId(Integer consoleId){
        return (root, query, criteriaBuilder) -> {
            Join<Game, Console> consoleJoin = root.join("console");
            return criteriaBuilder.equal(consoleJoin.get("id"), consoleId);
        };
    }

    private static Specification<Game> hasStatus(Status status) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("status"), status);
    }

    private static Specification<Game> hadStartedBefore(LocalDate startedDate){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startedDate);
    }

    public static Specification<Game> getSpecification(SearchGameDTO searchGameDTO) {
        Specification<Game> specification = Specification.where(null);
        if(StringUtils.isNotBlank(searchGameDTO.name())){
            specification = specification.and(GameSpecifications.hasNameLike(searchGameDTO.name()));
        }

        if(searchGameDTO.consoleId() != null && searchGameDTO.consoleId() != 0){
            specification = specification.and(GameSpecifications.hasGameId(searchGameDTO.consoleId()));
        }

        if(searchGameDTO.startedAfter() != null){
            specification = specification.and(GameSpecifications.hadStartedBefore(searchGameDTO.startedAfter()));
        }

        if(searchGameDTO.statusId() != null) {
            specification = specification.and(GameSpecifications.hasStatus(Status.getStatusFromInt(searchGameDTO.statusId())));
        }

        return specification;
    }
}
