package com.axoulotl.alextheque.controller;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.model.dto.input.SearchGameDTO;
import com.axoulotl.alextheque.model.dto.output.ErrorDTO;
import com.axoulotl.alextheque.model.dto.output.GameOutputDTO;
import com.axoulotl.alextheque.service.SearchServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    SearchServiceImpl searchServiceImpl;

    @Autowired
    public SearchController(SearchServiceImpl searchServiceImpl) {
        this.searchServiceImpl = searchServiceImpl;
    }

    @Operation(summary = "Search a game in DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "List of games matching the search criteria",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = GameOutputDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "An error occurred while trying to search games",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    }),
            @ApiResponse(responseCode = "500",
                    description = "A database error occurred while trying to search games",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    })
    })
    @PostMapping("/game")
    public ResponseEntity<Object> searchGame(@RequestBody SearchGameDTO searchGameDTO,
                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "15") int size){
        ResponseEntity<Object> responseEntity;
        try {
            responseEntity = ResponseEntity.ok(searchServiceImpl.searchGameWithCriteria(searchGameDTO, page, size));
        } catch (AlexthequeStandardError e) {
            responseEntity = ResponseEntity.internalServerError().body(new ErrorDTO(e.getComment(), e.getError()));
        }
        return responseEntity;
    }
}
