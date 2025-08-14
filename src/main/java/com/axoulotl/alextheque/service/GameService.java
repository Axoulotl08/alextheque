package com.axoulotl.alextheque.service;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.dto.input.GameUpdateDTO;
import com.axoulotl.alextheque.model.dto.output.GameOutputDTO;
import com.axoulotl.alextheque.model.dto.output.GamesOutputDTO;
import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.model.entity.enums.Status;
import com.axoulotl.alextheque.repository.ConsoleRepository;
import com.axoulotl.alextheque.repository.GameRepository;
import com.axoulotl.alextheque.service.converter.GameToGameDTOConverter;
import com.axoulotl.alextheque.service.validation.GameValidationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    public GameOutputDTO addGame(GameDTO gameDTO) throws AlexthequeStandardError {
        gameValidationService.validateGameInsert(gameDTO);

        Console console;
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
                .gameTime(0L)
                .startDate(null)
                .endDate(null)
                .status(Status.TO_START)
                .build();

        try {
            game = gameRepository.save(game);
        } catch (Exception e) {
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_DATABASE, "An error occurred while trying to save in DB.");
        }

        return converter.gameToGameDTO(game);
    }

    /**
     * Return all the game in DB.
     * Ordered by Game.name descending
     *
     * @return all the page of game
     */
    public GamesOutputDTO getAllGames(int page, int size) throws AlexthequeStandardError {

        gameValidationService.validatePageAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<Game> games = gameRepository.findAllByOrderByNameDesc(pageable);

        return GamesOutputDTO.builder()
                .nbGames(games.getTotalElements())
                .games(converter.gamesToListOfGames(games.getContent()))
                .totalPages(games.getTotalPages())
                .currentPage(page)
                .build();
    }

    /**
     * Return the game with the Id in parameter
     *
     * @return - the gameDTO
     */
    public GameOutputDTO getGameFromId(Integer id) throws AlexthequeStandardError {

        Game game;
        try{
            game = gameRepository.getReferenceById(id);
        }
        catch (EntityNotFoundException ex){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_DATABASE, "Game id Id : " + id + " dosn't exist");
        }

        return converter.gameToGameDTO(game);
    }


    public GameOutputDTO updateGameFromId(Integer id, GameUpdateDTO gameUpdateDTO) throws AlexthequeStandardError{
        gameValidationService.validateGameUpdate(gameUpdateDTO);

        Game game;
        try{
            game = gameRepository.getReferenceById(id);
        }
        catch (EntityNotFoundException ex){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_DATABASE, "Game id Id : " + id + " dosn't exist");
        }

        game.setGameTime(gameUpdateDTO.getGameTime());
        game.setEndDate(gameUpdateDTO.getEndDate());
        game.setStartDate(gameUpdateDTO.getStartDate());

        try {
            game = gameRepository.save(game);
        } catch (Exception e) {
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_DATABASE, "An error occurred while trying to save in DB.");
        }

        return converter.gameToGameDTO(game);
    }
}
