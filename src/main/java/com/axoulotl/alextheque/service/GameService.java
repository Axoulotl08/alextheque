package com.axoulotl.alextheque.service;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.dto.output.GameOutputDTO;
import com.axoulotl.alextheque.model.dto.output.GamesOutputDTO;
import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.repository.ConsoleRepository;
import com.axoulotl.alextheque.repository.GameRepository;
import com.axoulotl.alextheque.service.converter.GameToGameDTOConverter;
import com.axoulotl.alextheque.service.validation.GameValidationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GameService {

    private static Integer NB_GAME_PER_PAGES = 10;

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

        // Convert and return entity
        return ResponseEntity.ok(converter.gameToGameDTO(game));
    }

    /**
     * Return all the game in DB.
     *
     * @return all the page of game
     */
    public ResponseEntity<Object> getAllGames(int page, int size){

        Pageable pageable = PageRequest.of(page, size);
        Page<Game> games = gameRepository.findAllByOrderByNameDesc(pageable);

        GamesOutputDTO gamesOutputDTO = GamesOutputDTO.builder()
                .nbGames(games.getTotalElements())
                .games(converter.gamesToListOfGames(games.getContent()))
                .totalPages(games.getTotalPages())
                .currentPage(page)
                .build();
        return ResponseEntity.ok().body(gamesOutputDTO);
    }

    /**
     * Return the game with the Id in parameter
     *
     * @return - the gameDTO
     */
    public ResponseEntity<Object> getGameFromId(Integer id) throws AlexthequeStandardError {

        Game game;
        try{
            game = gameRepository.getReferenceById(id);
        }
        catch (EntityNotFoundException ex){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_DATABASE, "Game id Id : " + id + " dosn't exist");
        }

        return ResponseEntity.ok(converter.gameToGameDTO(game));
    }
}
