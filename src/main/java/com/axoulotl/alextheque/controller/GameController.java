package com.axoulotl.alextheque.controller;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.dto.output.ErrorDTO;
import com.axoulotl.alextheque.model.dto.output.GameOutputDTO;
import com.axoulotl.alextheque.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                    @Content(mediaType = "application/json", schema = @Schema(implementation = GameOutputDTO.class))
            }),
            @ApiResponse(responseCode = "400",
                    description = "An error occurred while trying to add the game",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AlexthequeStandardError.class))
                }),
            @ApiResponse(responseCode = "500",
                    description = "A database error occurred while trying to add the game",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AlexthequeStandardError.class))
            })
    })
    @PostMapping("/game")
    public ResponseEntity<Object> addGame(@RequestBody GameDTO gameDTO) {
        ResponseEntity<Object> responseEntity = null;
        try{
            responseEntity =  gameService.addGame(gameDTO);
        }
        catch (AlexthequeStandardError ex){
            if(ex.getError() == StandardErrorEnum.ERROR_INPUT)
                responseEntity =  ResponseEntity.badRequest().body(new ErrorDTO(ex.getComment(), ex.getError()));
            if(ex.getError() == StandardErrorEnum.ERROR_DATABASE)
                responseEntity =  ResponseEntity.internalServerError().body(new ErrorDTO(ex.getComment(), ex.getError()));
        }
        return responseEntity;
    }

    @Operation(summary = "Get all games in collection")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieve the games",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "An error occurred while trying to get the game",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AlexthequeStandardError.class))
                    }),
            @ApiResponse(responseCode = "500",
                    description = "A database error occurred while trying to get the game",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AlexthequeStandardError.class))
                    })
    })
    @GetMapping("/game")
    public ResponseEntity<Object> getAllGames(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "15") int size){
        ResponseEntity<Object> responseEntity;

        try{
            responseEntity = gameService.getAllGames(page, size);
        } catch (Exception ex){
            responseEntity = ResponseEntity.internalServerError().build();
        }
        return responseEntity;
    }
}
