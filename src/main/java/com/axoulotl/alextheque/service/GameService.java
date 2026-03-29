package com.axoulotl.alextheque.service;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.dto.input.GameUpdateDTO;
import com.axoulotl.alextheque.model.dto.output.GameOutputDTO;
import com.axoulotl.alextheque.model.dto.output.GamesOutputDTO;

public interface GameService {
    GameOutputDTO addGame(GameDTO gameDTO) throws AlexthequeStandardError;
    GamesOutputDTO getAllGames(int page, int size) throws AlexthequeStandardError;
    GameOutputDTO getGameFromId(Integer id) throws AlexthequeStandardError;
    GameOutputDTO updateGameFromId(Integer id, GameUpdateDTO gameUpdateDTO) throws AlexthequeStandardError;
}
