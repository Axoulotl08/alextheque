package com.axoulotl.alextheque.services.validation;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.dto.input.GameUpdateDTO;
import com.axoulotl.alextheque.service.validation.GameValidationService;
import com.axoulotl.alextheque.services.utils.UtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameValidationServiceTest {
    private GameValidationService validationService;

    @BeforeEach
    void setUp(){
        this.validationService = new GameValidationService();
    }

    @Test
    @Order(1)
    @DisplayName("Normal uses case")
    void whenValidateGameInsertDTO_GivenValidGameDTO_thenNoExceptionThrown() throws AlexthequeStandardError {
        GameDTO gameDTO = new GameDTO();
        gameDTO.setConsole(1);
        gameDTO.setName("Test");
        gameDTO.setInbox(true);

        assertThatCode(() -> validationService.validateGameInsert(gameDTO))
                .doesNotThrowAnyException();
    }

    @Test
    @Order(2)
    @DisplayName("Insert Game with Wring name")
    void whenValidateGameInsertDTO_GivenDTOWithWrongName_thenExceptionIsThrown() throws AlexthequeStandardError{
        GameDTO gameDTO = UtilsTest.createGameDTO(1);

        gameDTO.setName("   ");

        AlexthequeStandardError thrownException = assertThrows(AlexthequeStandardError.class,
                () -> validationService.validateGameInsert(gameDTO));

        assertThat(thrownException.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownException.getComment()).contains("The name should not be empty or null.");
    }

    @Test
    @Order(3)
    @DisplayName("Insert Game with Wrong Console Id")
    void whenValidateGameInsertDTO_GivenDTOWithWrongConsoleId_theExceptionIsThrown() throws AlexthequeStandardError{
        GameDTO gameDTO = UtilsTest.createGameDTO(0);

        AlexthequeStandardError thrownException = assertThrows(AlexthequeStandardError.class,
                () -> validationService.validateGameInsert(gameDTO));

        assertThat(thrownException.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownException.getComment()).isEqualTo("Console Id should be greater than 0.");
    }

    @Test
    @Order(4)
    @DisplayName("Insert Game with null DTO")
    void whenValidateGameInsertDTO_GivenNullDTO_thenExceptionIsThrown() throws AlexthequeStandardError{

        AlexthequeStandardError thrownException = assertThrows(AlexthequeStandardError.class,
                () -> validationService.validateGameInsert(null));

        assertThat(thrownException.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownException.getComment()).isEqualTo("The GameDTO should not be null.");
    }

    @Test
    @Order(5)
    @DisplayName("Update Game : normal use case")
    void whenValidateUpdateDTo_GivenValidUpdateDTO_thenNoExceptionThrown() {
        GameUpdateDTO gameUpdateDTO = UtilsTest.createUpdateDTO();

        assertThatCode(() -> validationService.validateGameUpdate(gameUpdateDTO))
                .doesNotThrowAnyException();
    }

    @Test
    @Order(6)
    @DisplayName("Update Game : With Start date in future")
    void whenValidateUpdateDTO_givenDTOWithStartDateInFuture_thenExceptionIsThrown() throws AlexthequeStandardError{
        GameUpdateDTO gameUpdateDTO = UtilsTest.createUpdateDTO();

        gameUpdateDTO.setStartDate(LocalDate.now().plusDays(3));

        AlexthequeStandardError thrownError = assertThrows(AlexthequeStandardError.class,
                () -> validationService.validateGameUpdate(gameUpdateDTO));

        assertThat(thrownError.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownError.getComment()).isEqualTo("The start date should be before or today.");
    }

    @Test
    @Order(7)
    @DisplayName("Update Game : With end date but no start date")
    void whenValidateUpdateDTO_givenDTOWithEndDateButNoStartDate_thenExceptionIsThrown() throws AlexthequeStandardError{
        GameUpdateDTO gameUpdateDTO = UtilsTest.createUpdateDTO();
        gameUpdateDTO.setStartDate(null);

        AlexthequeStandardError thrownError = assertThrows(AlexthequeStandardError.class,
                () -> validationService.validateGameUpdate(gameUpdateDTO));

        assertThat(thrownError.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownError.getComment()).isEqualTo("You must have a start date before entering a end date.");
    }

    @Test
    @Order(8)
    @DisplayName("Update Game : With end date before start date")
    void whenValidateUpdateDTO_givenDTOEndDateBeforeThanStartDate_thenExceptionIsThrown() throws AlexthequeStandardError{
        GameUpdateDTO gameUpdateDTO = UtilsTest.createUpdateDTO();
        gameUpdateDTO.setStartDate(LocalDate.now());
        gameUpdateDTO.setEndDate(LocalDate.now().minusDays(10));

        AlexthequeStandardError thrownError = assertThrows(AlexthequeStandardError.class,
                () -> validationService.validateGameUpdate(gameUpdateDTO));

        assertThat(thrownError.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownError.getComment()).isEqualTo("The end date should be after the start date.");
    }

    @Test
    @Order(9)
    @DisplayName("Update Game : Negative game time")
    void whenValidateUpdateDTO_givenDTOWithNegativeGameTime_thenExceptionIsThrown() throws AlexthequeStandardError{
        GameUpdateDTO gameUpdateDTO = UtilsTest.createUpdateDTO();

        gameUpdateDTO.setGameTime(-1L);

        AlexthequeStandardError thrownError = assertThrows(AlexthequeStandardError.class,
                () -> validationService.validateGameUpdate(gameUpdateDTO));

        assertThat(thrownError.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownError.getComment()).isEqualTo("Game time should be superior to 0.");
    }

    @Test
    @Order(10)
    @DisplayName("Update Game : With end date in future")
    void whenValidateUpdateDTO_givenDTOWithEndDateInFuture_thenExceptionIsThrown() throws AlexthequeStandardError{
        GameUpdateDTO gameUpdateDTO = UtilsTest.createUpdateDTO();

        gameUpdateDTO.setEndDate(LocalDate.now().plusDays(3));

        AlexthequeStandardError thrownError = assertThrows(AlexthequeStandardError.class,
                () -> validationService.validateGameUpdate(gameUpdateDTO));

        assertThat(thrownError.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownError.getComment()).isEqualTo("The end date should be before or today.");
    }
}
