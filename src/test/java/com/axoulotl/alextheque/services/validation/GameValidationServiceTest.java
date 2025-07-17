package com.axoulotl.alextheque.services.validation;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.service.validation.GameValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameValidationServiceTest {
    private GameValidationService validationService;

    @BeforeEach
    void setUp(){
        this.validationService = new GameValidationService();
    }

    @Test
    void whenValidateConsoleInsert_GivenValidGameDTO_thenNoExceptionThrown() throws AlexthequeStandardError {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setConsole(1);
        gameDTO.setName("Test");
        gameDTO.setInbox(true);

        validationService.validateGameInsert(gameDTO);
    }
}
