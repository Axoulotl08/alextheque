package com.axoulotl.alextheque.controller;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.ConsoleDTO;
import com.axoulotl.alextheque.model.dto.output.ErrorDTO;
import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.service.ConsoleService;
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
public class ConsoleController {

    ConsoleService consoleService;

    @Autowired
    public ConsoleController(ConsoleService consoleService){
        this.consoleService = consoleService;
    }

    @Operation(summary = "Add a console in collection")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Successfully added the console to the collection",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Console.class))
            }),
            @ApiResponse(responseCode = "400",
            description = "An error occurred while trying to add the console",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
            }),
            @ApiResponse(responseCode = "500",
            description = "An technical error occurred while trying to add the console",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
            })
    })
    @PostMapping("/console")
    public ResponseEntity<Object> addConsole(@RequestBody ConsoleDTO consoleDTO){
        ResponseEntity<Object> responseEntity = null;
        try {
            responseEntity =  ResponseEntity.ok(consoleService.addConsole(consoleDTO));
        } catch (AlexthequeStandardError ex){
            if(ex.getError() == StandardErrorEnum.ERROR_INPUT)
                responseEntity =  ResponseEntity.badRequest().body(new ErrorDTO(ex.getComment(), ex.getError()));
            if(ex.getError() == StandardErrorEnum.ERROR_DATABASE)
                responseEntity =  ResponseEntity.internalServerError().body(new ErrorDTO(ex.getComment(), ex.getError()));
        }
        return responseEntity;
    }

    @Operation(summary = "Get all console in collection")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully added the console to the collection",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Console.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "An error occurred while trying to add the console",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    }),
            @ApiResponse(responseCode = "500",
                    description = "An technical error occurred while trying to add the console",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))
                    })
    })
    @GetMapping("/console")
    public ResponseEntity<Object> getConsole(){
        ResponseEntity<Object> responseEntity = null;
        try{
            responseEntity = ResponseEntity.ok().body(consoleService.getAllConsole());
        } catch (Exception e) {
            responseEntity = ResponseEntity.internalServerError().build();
        }
        return responseEntity;
    }
}
