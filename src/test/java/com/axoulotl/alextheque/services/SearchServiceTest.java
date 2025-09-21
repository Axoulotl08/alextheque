package com.axoulotl.alextheque.services;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.dto.input.SearchGameDTO;
import com.axoulotl.alextheque.model.dto.output.GameOutputDTO;
import com.axoulotl.alextheque.model.dto.output.GamesOutputDTO;
import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.repository.GameRepository;
import com.axoulotl.alextheque.service.SearchService;
import com.axoulotl.alextheque.service.converter.GameToGameDTOConverter;
import com.axoulotl.alextheque.services.utils.UtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @InjectMocks
    private SearchService searchService;

    @Mock
    private GameRepository gameRepository;


    @Mock
    private GameToGameDTOConverter converter;


    private SearchGameDTO searchGameDTO;
    private List<Game> gameList;
    private GamesOutputDTO gamesOutputDTO;
    private Page<Game> gamePage;

    @BeforeEach
    void setUp() {
        // Initialisation des données de test
        searchGameDTO = SearchGameDTO.builder()
                .name("Zelda")
                .consoleId(1)
                .startedAfter(LocalDate.of(2023, 1, 1))
                .statusId(1)
                .build();

        // Création de jeux fictifs
        Game game1 = new Game();
        Game game2 = new Game();
        gameList = Arrays.asList(game1, game2);

//        gamesOutputDTO = GamesOutputDTO.builder()
//                .currentPage(0)
//                .nbGames(2L)
//                .totalPages(1)
//                .games(Arrays.asList(gameDTO1, gameDTO2))
//                .build();

        // Création d'une page
        gamePage = new PageImpl<>(gameList, PageRequest.of(0, 10), 2);
    }

    @Test
    void searchGameWithCriteria_Success() throws AlexthequeStandardError {
        // Arrange
        int page = 0;
        int size = 10;

        when(gameRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(gamePage);
//        when(converter.gamesToListOfGames(gameList))
//                .thenReturn(gameList);

        // Act
        GamesOutputDTO result = searchService.searchGameWithCriteria(searchGameDTO, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getNbGames());
//        assertEquals(gameDTOList, result.getGames());
        assertEquals(1, result.getTotalPages());
        assertEquals(page, result.getCurrentPage());

        verify(gameRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(converter, times(1)).gamesToListOfGames(gameList);
    }
}
