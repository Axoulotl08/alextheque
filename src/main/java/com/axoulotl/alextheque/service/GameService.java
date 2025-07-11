package com.axoulotl.alextheque.service;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.dto.output.GameOutputDTO;
import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.repository.ConsoleRepository;
import com.axoulotl.alextheque.repository.GameRepository;
import com.axoulotl.alextheque.service.converter.GameToGameDTOConverter;
import com.axoulotl.alextheque.service.validation.GameValidationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GameService {

    GameRepository gameRepository;
    GameValidationService gameValidationService;
    ConsoleRepository consoleRepository;
    GameToGameDTOConverter converter;

    @Autowired
    public GameService(GameRepository gameRepository,
                       GameValidationService gameValidationService,
                       ConsoleRepository consoleRepository,
                       GameToGameDTOConverter converter) {
        this.gameRepository = gameRepository;
        this.gameValidationService = gameValidationService;
        this.consoleRepository = consoleRepository;
        this.converter = converter;
    }

    /**
     * Add a game in collection after validation the json content
     *
     * @param gameDTO - gameDTO
     * @return the entity created in database
     * @throws AlexthequeStandardError in case of technical or functional error
     */
    public ResponseEntity<Object> addGame(GameDTO gameDTO) throws AlexthequeStandardError {
        gameValidationService.validateGameInsert(gameDTO);

        Console console = new Console();
        try{
            console = consoleRepository.getReferenceById(gameDTO.getConsole());
        }
        catch (EntityNotFoundException ex){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_DATABASE, "There is no console with this ID.");
        }

        Game game = Game.builder()
                .name(gameDTO.getName())
                .console(console)
                .inbox(gameDTO.getInbox())
                .build();

        try {
            gameRepository.save(game);
        } catch (Exception e) {
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_DATABASE, "An error occurred while trying to save in DB.");
        }

        return ResponseEntity.ok(converter.gameToGameDTO(game));
    }

}
