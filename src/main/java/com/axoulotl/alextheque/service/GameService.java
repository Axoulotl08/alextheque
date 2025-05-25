package com.axoulotl.alextheque.service;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.repository.GameRepository;
import com.axoulotl.alextheque.service.validation.GameValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    GameRepository gameRepository;
    GameValidationService gameValidationService;

    @Autowired
    public GameService(GameRepository gameRepository,
                       GameValidationService gameValidationService) {
        this.gameRepository = gameRepository;
        this.gameValidationService = gameValidationService;
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

        Game game = Game.builder()
                .name(gameDTO.getName())
                .console(gameDTO.getConsole())
                .inbox(gameDTO.getInbox())
                .build();

        try {
            gameRepository.save(game);
        } catch (Exception e) {
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_DATABASE, "Erreur lors de la sauvegarde en BDD");
        }
        return ResponseEntity.ok(game);
    }
}
