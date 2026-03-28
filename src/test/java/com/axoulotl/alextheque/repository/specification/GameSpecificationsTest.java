package com.axoulotl.alextheque.repository.specification;

import com.axoulotl.alextheque.TestContenerConfig;
import com.axoulotl.alextheque.model.dto.input.SearchGameDTO;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

@Sql(scripts = "/sql/init-specification.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class GameSpecificationsTest extends TestContenerConfig {

    @Autowired
    private GameRepository gameRepository;

    @Test
    @Transactional
    void whenSearchByName_shouldReturnGameWithMatchingName(){
        SearchGameDTO searchGameDTO = new SearchGameDTO(null, "God", null, null);

        Specification<Game> specification = GameSpecifications.getSpecification(searchGameDTO);
        List<Game> results = gameRepository.findAll(specification);

        assertThat(results).hasSize(2);
        assertThat(results)
                .extracting(Game::getName)
                .containsExactlyInAnyOrder("Ragnarok God Of War", "God Of War");
    }


    @Test
    @Transactional
    void whenSearchByStartedAfter_shouldReturnGameStartedAfter(){
        SearchGameDTO searchGameDTO = new SearchGameDTO(null, null, LocalDate.of(2025,12,12), null);

        Specification<Game> specification = GameSpecifications.getSpecification(searchGameDTO);
        List<Game> results = gameRepository.findAll(specification);

        assertThat(results).hasSize(2);
        assertThat(results)
                .extracting(Game::getName)
                .containsExactlyInAnyOrder("BOTW", "Ragnarok God Of War");
    }

    @Test
    @Transactional
    void whenSearchByStatus_shouldReturnGameWithMatchingStatus(){
        SearchGameDTO searchGameDTO = new SearchGameDTO(null, null, null, 4);

        Specification<Game> specification = GameSpecifications.getSpecification(searchGameDTO);
        List<Game> results = gameRepository.findAll(specification);

        assertThat(results).hasSize(3);
        assertThat(results)
                .extracting(Game::getName)
                .containsExactlyInAnyOrder("MK8", "Ragnarok God Of War", "God Of War");
    }

    @Test
    @Transactional
    void whenSearchByConsoleId_shouldReturnGameWithMatchingConsoleId(){
        SearchGameDTO searchGameDTO = new SearchGameDTO(1, null, null, null);

        Specification<Game> specification = GameSpecifications.getSpecification(searchGameDTO);
        List<Game> results = gameRepository.findAll(specification);

        assertThat(results).hasSize(3);
        assertThat(results)
                .extracting(Game::getName)
                .containsExactlyInAnyOrder("TLOU", "God Of War", "Ragnarok God Of War");
    }

    @Test
    @Transactional
    void whenSearchWithFullCriteria_shouldReturnGameWithMatchingFullCriteria(){
        SearchGameDTO searchGameDTO = new SearchGameDTO(1, "God", LocalDate.of(2025,12,12), 4);

        Specification<Game> specification = GameSpecifications.getSpecification(searchGameDTO);
        List<Game> results = gameRepository.findAll(specification);

        assertThat(results).hasSize(1);
        assertThat(results)
                .extracting(Game::getName)
                .containsExactlyInAnyOrder("Ragnarok God Of War");
    }
}
