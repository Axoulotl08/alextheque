package com.axoulotl.alextheque.service;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.dto.input.GameUpdateDTO;
import com.axoulotl.alextheque.model.dto.output.GameOutputDTO;
import com.axoulotl.alextheque.model.dto.output.GamesOutputDTO;

/**
 * Game related service
 * <p>
 *     This interface defines jobs operation for creating,
 *     getting, deleting and updating games
 * </p>
 *
 * @version 1.0
 * @since 2026-01-01
 */
public interface GameService {
    /**
     * Create and save a new game into the database
     * <p>
     *     Validate the data entry (name, consoleId and inbox boolean) before the creation
     * </p>
     *
     * @param gameDTO - GameDTO with all the data to create (name, console id and inbox)
     * @return the created game with its unique id
     * @throws AlexthequeStandardError in case of an error during the save into database
     */
    GameOutputDTO addGame(GameDTO gameDTO) throws AlexthequeStandardError;

    /**
     * Get all the games saved in the databases following the pagination defines in the entry data
     * <p>
     *     Games are returned into a dto list which include all the information
     *     of the games
     *     Also return the games count, the current page and the number of total page of the pagination
     *     Default values for pagination :
     *     - page : 0
     *     - page size : 15
     * </p>
     *
     *
     * @param page - the current page to return
     * @param size - the number of game per pages
     * @return all the games following the current pagination. Or an empty list if there are any/
     * @throws AlexthequeStandardError in case of an error during the pagination process
     */
    GamesOutputDTO getAllGames(int page, int size) throws AlexthequeStandardError;

    /**
     * Get all the information of a game
     * <p>
     *     Game is returned into a dto which include all the information of the game
     * </p>
     *
     * @param id - The unique id of the game to retreive from the database
     * @return the current game corresponding to the unique id from the database
     * @throws AlexthequeStandardError if there is no game with this unique id
     */
    GameOutputDTO getGameFromId(Integer id) throws AlexthequeStandardError;

    /**
     *
     * @param id
     * @param gameUpdateDTO
     * @return
     * @throws AlexthequeStandardError
     */
    GameOutputDTO updateGameFromId(Integer id, GameUpdateDTO gameUpdateDTO) throws AlexthequeStandardError;
}
