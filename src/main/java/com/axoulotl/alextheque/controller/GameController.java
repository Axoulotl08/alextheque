package com.axoulotl.alextheque.controller;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.entity.Game;
import com.axoulotl.alextheque.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class GameController {

    GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Add a new game in collection")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Successfully added the game to the collection",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = GameDTO.class))
            }),
            @ApiResponse(responseCode = "400",
                    description = "An error occured while trying to add the game",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AlexthequeStandardError.class))
                    })
    })
    @PostMapping("/game")
    public ResponseEntity<Object> addGame(@RequestBody GameDTO gameDTO) {
        try{
            return gameService.addGame(gameDTO);
        }
        catch (AlexthequeStandardError ex){
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<Game> getGameById(@RequestParam int id) {
        return ResponseEntity.ok().build();
    }
}
