package com.axoulotl.alextheque.service.converter;


import com.axoulotl.alextheque.model.dto.output.ConsoleOutputDTO;
import com.axoulotl.alextheque.model.dto.output.GameOutputDTO;
import com.axoulotl.alextheque.model.entity.Game;
import org.springframework.stereotype.Service;

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
                .console(ConsoleOutputDTO.builder()
                        .launchDate(game.getConsole().getLaunchDate())
                        .name(game.getConsole().getName())
                        .manufacturer(game.getConsole().getManufacturer())
                        .build())
                .build();
    }
}
