package com.axoulotl.alextheque.services;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.model.dto.input.SearchGameDTO;
import com.axoulotl.alextheque.model.dto.output.GamesOutputDTO;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.repository.GameRepository;
import com.axoulotl.alextheque.service.SearchService;
import com.axoulotl.alextheque.service.converter.GameToGameDTOConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        // Initialisation des données de te
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
