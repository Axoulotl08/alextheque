package com.axoulotl.alextheque.services;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.dto.output.GameOutputDTO;
import com.axoulotl.alextheque.model.dto.output.GamesOutputDTO;
import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.repository.ConsoleRepository;
import com.axoulotl.alextheque.repository.GameRepository;
import com.axoulotl.alextheque.service.GameService;
import com.axoulotl.alextheque.service.converter.GameToGameDTOConverter;
import com.axoulotl.alextheque.services.utils.UtilsTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    GameRepository gameRepository;

    @Mock
    ConsoleRepository consoleRepository;

    @Mock
    GameToGameDTOConverter converter;

    @InjectMocks
    GameService gameService;

    @Test
    void whenAddGame_GivenGoodDto_TheGameIsSaved() throws AlexthequeStandardError {
        Game game = UtilsTest.createGame(1);
        game.setName("TestDTO");

        Optional<Console> console = Optional.of(UtilsTest.createConsole(1));

        GameDTO gameDTO = UtilsTest.createGameDTO(1);

        when(consoleRepository.findById(any(Integer.class))).thenReturn(console);
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        when(converter.gameToGameDTO(any(Game.class))).thenCallRealMethod();

        GameOutputDTO result = gameService.addGame(gameDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("TestDTO");

        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void whenAddGame_GivenDTOButBDDError_thenExceptionIsThrown(){
        GameDTO gameDTO = UtilsTest.createGameDTO(1);
        Optional<Console> console = Optional.of(UtilsTest.createConsole(1));

        when(consoleRepository.findById(any(Integer.class))).thenReturn(console);
        doThrow(new RuntimeException("Simulated DB Connection error")).when(gameRepository).save(any(Game.class));

        AlexthequeStandardError thrownException = assertThrows(AlexthequeStandardError.class, () -> gameService.addGame(gameDTO));

        verify(gameRepository, times(1)).save(any(Game.class));
        assertThat(thrownException.getError()).isEqualTo(StandardErrorEnum.ERROR_DATABASE);
        assertThat(thrownException.getComment()).contains("An error occurred while trying to save in DB.");
    }

    @Test
    void whenGetGame_WithGoodData_thenGamesAreReturned() throws AlexthequeStandardError {
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Game game = new Game();
        game.setId(1);
        game.setName("Test Game");
        List<Game> gameList = Collections.singletonList(game);

        Page<Game> gamePage = new PageImpl<>(gameList, pageable, 1L);

        when(gameRepository.findAllByOrderByNameDesc(any(Pageable.class))).thenReturn(gamePage);

        GameOutputDTO gameOutputDTO = GameOutputDTO.builder()
                .name("Test Game")
                .id(1)
                .build();
        List<GameOutputDTO> gameOutputDTOList = Collections.singletonList(gameOutputDTO);
        when(converter.gamesToListOfGames(anyList())).thenReturn(gameOutputDTOList);

        GamesOutputDTO result = gameService.getAllGames(page, size);

        assertThat(result).isNotNull();
        assertThat(result.getNbGames()).isEqualTo(11L);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getCurrentPage()).isEqualTo(page);
        assertThat(result.getGames().getFirst().getName()).isEqualTo("Test Game");

        verify(gameRepository, times(1)).findAllByOrderByNameDesc(pageable);
        verify(converter, times(1)).gamesToListOfGames(gameList);
    }
}
