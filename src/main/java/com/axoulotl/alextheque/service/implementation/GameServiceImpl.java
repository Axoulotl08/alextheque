package com.axoulotl.alextheque.service.implementation;

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
import com.axoulotl.alextheque.service.GameService;
import com.axoulotl.alextheque.service.converter.GameToGameDTOConverter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ConsoleRepository consoleRepository;
    private final GameToGameDTOConverter converter;

    /**
     * Add a game in collection after validation the JSON content
     *
     * @param gameDTO - gameDTO
     * @return the entity created in database
     * @throws AlexthequeStandardError in case of technical or functional error
     */
    @Override
    public GameOutputDTO addGame(GameDTO gameDTO) throws AlexthequeStandardError {
        Optional<Console> console;
        try{
            console = consoleRepository.findById(gameDTO.console());
        }
        catch (EntityNotFoundException ex){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_DATABASE, "There is no console with this ID.");
        }

        if(console.isEmpty()){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_DATABASE, "There is no console with this ID.");
        }

        Game game = Game.builder()
                .name(gameDTO.name())
                .console(console.get())
                .inbox(gameDTO.inbox())
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
    @Override
    public GamesOutputDTO getAllGames(int page, int size) throws AlexthequeStandardError {
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
    @Override
    public GameOutputDTO getGameFromId(Integer id) throws AlexthequeStandardError {

        Optional<Game> game;
        try{
            game = gameRepository.findById(id);
        }
        catch (EntityNotFoundException ex){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_DATABASE, "Game id Id : " + id + " dosn't exist");
        }

        return converter.gameToGameDTO(game.get());
    }

    @Override
    public GameOutputDTO updateGameFromId(Integer id, GameUpdateDTO gameUpdateDTO) throws AlexthequeStandardError{
        Game game;
        try{
            game = gameRepository.getReferenceById(id);
        }
        catch (EntityNotFoundException ex){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_DATABASE, "Game id Id : " + id + " dosn't exist");
        }

        game.setGameTime(gameUpdateDTO.gameTime());
        game.setEndDate(gameUpdateDTO.endDate());
        game.setStartDate(gameUpdateDTO.startDate());

        try {
            game = gameRepository.save(game);
        } catch (Exception e) {
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_DATABASE, "An error occurred while trying to save in DB.");
        }

        return converter.gameToGameDTO(game);
    }
}
