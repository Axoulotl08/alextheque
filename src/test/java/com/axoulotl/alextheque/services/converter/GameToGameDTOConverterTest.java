package com.axoulotl.alextheque.services.converter;

import com.axoulotl.alextheque.model.dto.output.GameOutputDTO;
import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.service.converter.GameToGameDTOConverter;
import com.axoulotl.alextheque.services.utils.UtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GameToGameDTOConverterTest {

    GameToGameDTOConverter converter;

    @BeforeEach
    void setUp(){
        this.converter = new GameToGameDTOConverter();
    }

    @Test
    void whenAddGame_GivenGame_thenIsConvertedToGameOutputDTO(){
        Game game = UtilsTest.createGame(1);

        GameOutputDTO outputDTO = converter.gameToGameDTO(game);
        assertThat(outputDTO).isNotNull();
        assertThat(outputDTO.getId()).isEqualTo(game.getId());
        assertThat(outputDTO.getInbox()).isEqualTo(game.getInbox());
        assertThat(outputDTO.getName()).isEqualTo(game.getName());
        assertThat(outputDTO.getConsole().getId()).isEqualTo(game.getConsole().getId());
    }

    @Test
    void givenGetGame_GivenListOfGame_thenIsConvertedIntoGamesOutputDTO(){
        List<Game> gameList = new ArrayList<>();
        for (int i = 0; i <= 10; i++){
            gameList.add(UtilsTest.createGame(i));
        }

        List<GameOutputDTO> outputDTOList = converter.gamesToListOfGames(gameList);
        assertThat(outputDTOList).isNotNull();
        assertThat(outputDTOList.size()).isEqualTo(gameList.size());
        assertThat(outputDTOList.getFirst().getId()).isEqualTo(gameList.getFirst().getId());
    }
}
