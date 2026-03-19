package com.axoulotl.alextheque.services;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.dto.input.SearchGameDTO;
import com.axoulotl.alextheque.model.dto.output.GameOutputDTO;
import com.axoulotl.alextheque.model.dto.output.GamesOutputDTO;
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

import java.util.ArrayList;
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


    @BeforeEach
    void setUp() {
    }

    @Test
    void searchGameWithCriteria_givenGoodDto_thenGameMatchingCriteriaAreReturned() throws AlexthequeStandardError {

        int page = 0;
        int size = 10;

        Game game = UtilsTest.createGame(1);
        List<Game> gameList = List.of(game);
        SearchGameDTO searchGameDTO = new SearchGameDTO();
        searchGameDTO.setName("Test");

        Page<Game> gamePage = new PageImpl<>(gameList, PageRequest.of(page, size), gameList.size());

        when(gameRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(gamePage);

        when(converter.gamesToListOfGames(anyList()))
                .thenCallRealMethod();

        GamesOutputDTO result = searchService.searchGameWithCriteria(searchGameDTO, page, size);

        assertNotNull(result);
        assertEquals(1, result.getNbGames());
        assertEquals(1, result.getTotalPages());
        assertEquals(page, result.getCurrentPage());
        assertEquals(1, result.getGames().size());

        verify(gameRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(converter, times(1)).gamesToListOfGames(gameList);
    }
}
