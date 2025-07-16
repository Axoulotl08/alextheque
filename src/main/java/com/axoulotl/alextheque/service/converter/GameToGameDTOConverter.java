package com.axoulotl.alextheque.service.converter;


import com.axoulotl.alextheque.model.dto.output.ConsoleOutputDTO;
import com.axoulotl.alextheque.model.dto.output.GameOutputDTO;
import com.axoulotl.alextheque.model.entity.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameToGameDTOConverter {

    /**
     * Convert a game to a GameDTO for output
     * @param game - the game to convert
     * @return the DTO
     */
    public GameOutputDTO gameToGameDTO(Game game){
        return GameOutputDTO.builder()
                .id(game.getId())
                .name(game.getName())
                .inbox(game.getInbox())
                .startDate(game.getStartDate())
                .gameTime(game.getGameTime())
                .endDate(game.getEndDate())
                .console(ConsoleOutputDTO.builder()
                        .launchDate(game.getConsole().getLaunchDate())
                        .name(game.getConsole().getName())
                        .manufacturer(game.getConsole().getManufacturer())
                        .zone(game.getConsole().getZone())
                        .creationDate(game.getConsole().getCreationDate())
                        .build())
                .build();
    }

    /**
     * Convert a list of Game entity to a list of GameOutputDTO
     * @param gameList - the list of game to convert
     * @return the list of converted DTO
     */
    public List<GameOutputDTO> gamesToListOfGames(List<Game> gameList){
        return gameList.stream().map(this::gameToGameDTO).toList();
    }
}
