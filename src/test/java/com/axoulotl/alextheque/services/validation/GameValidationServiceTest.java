package com.axoulotl.alextheque.services.validation;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.dto.input.GameUpdateDTO;
import com.axoulotl.alextheque.validation.GameValidationService;
import com.axoulotl.alextheque.services.utils.UtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Deprecated
@Disabled("Old test. Replaced by validator test.")
public class GameValidationServiceTest {
    private GameValidationService validationService;

    private static final LocalDate now = LocalDate.now();

    @BeforeEach
    void setUp() {
        this.validationService = new GameValidationService();
    }

    @Test
    @DisplayName("Normal uses case")
    void whenValidateGameInsertDTO_GivenValidGameDTO_thenNoExceptionThrown() {
        GameDTO gameDTO = new GameDTO("TestDTO", 1, Boolean.TRUE);

        assertThatCode(() -> validationService.validateGameInsert(gameDTO))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Insert Game with Wring name")
    void whenValidateGameInsertDTO_GivenDTOWithWrongName_thenExceptionIsThrown() {
        GameDTO gameDTO = UtilsTest.createGameDTO(1);

//        gameDTO.setName("   ");

        AlexthequeStandardError thrownException = assertThrows(AlexthequeStandardError.class,
                () -> validationService.validateGameInsert(gameDTO));

        assertThat(thrownException.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownException.getComment()).contains("The name should not be empty or null.");
    }

    @Test
    @DisplayName("Insert Game with Wrong Console Id")
    void whenValidateGameInsertDTO_GivenDTOWithWrongConsoleId_theExceptionIsThrown() {
        GameDTO gameDTO = UtilsTest.createGameDTO(0);

        AlexthequeStandardError thrownException = assertThrows(AlexthequeStandardError.class,
                () -> validationService.validateGameInsert(gameDTO));

        assertThat(thrownException.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownException.getComment()).isEqualTo("Console Id should be greater than 0.");
    }

    @Test
    @DisplayName("Insert Game with null DTO")
    void whenValidateGameInsertDTO_GivenNullDTO_thenExceptionIsThrown() {

        AlexthequeStandardError thrownException = assertThrows(AlexthequeStandardError.class,
                () -> validationService.validateGameInsert(null));

        assertThat(thrownException.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownException.getComment()).isEqualTo("The GameDTO should not be null.");
    }

    @Test
    @DisplayName("Update Game : normal use case")
    void whenValidateUpdateDTo_GivenValidUpdateDTO_thenNoExceptionThrown() {
        GameUpdateDTO gameUpdateDTO = UtilsTest.createUpdateDTO();

        assertThatCode(() -> validationService.validateGameUpdate(gameUpdateDTO))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Update Game : With Start date in future")
    void whenValidateUpdateDTO_givenDTOWithStartDateInFuture_thenExceptionIsThrown() {
        GameUpdateDTO gameUpdateDTO = new GameUpdateDTO(now.plusDays(3), null, null);

        AlexthequeStandardError thrownError = assertThrows(AlexthequeStandardError.class,
                () -> validationService.validateGameUpdate(gameUpdateDTO));

        assertThat(thrownError.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownError.getComment()).isEqualTo("The start date should be before or today.");
    }

    @Test
    @DisplayName("Update Game : With end date but no start date")
    void whenValidateUpdateDTO_givenDTOWithEndDateButNoStartDate_thenExceptionIsThrown() {
        GameUpdateDTO gameUpdateDTO = new GameUpdateDTO(null, LocalDate.now(), 1L);

        AlexthequeStandardError thrownError = assertThrows(AlexthequeStandardError.class,
                () -> validationService.validateGameUpdate(gameUpdateDTO));

        assertThat(thrownError.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownError.getComment()).isEqualTo("You must have a start date before entering a end date.");
    }

    @Test
    @DisplayName("Update Game : With end date before start date")
    void whenValidateUpdateDTO_givenDTOEndDateBeforeThanStartDate_thenExceptionIsThrown() {
        GameUpdateDTO gameUpdateDTO = new GameUpdateDTO(now, now.minusDays(10), null);

        AlexthequeStandardError thrownError = assertThrows(AlexthequeStandardError.class,
                () -> validationService.validateGameUpdate(gameUpdateDTO));

        assertThat(thrownError.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownError.getComment()).isEqualTo("The end date should be after the start date.");
    }

    @Test
    @DisplayName("Update Game : Negative game time")
    void whenValidateUpdateDTO_givenDTOWithNegativeGameTime_thenExceptionIsThrown() {
        GameUpdateDTO gameUpdateDTO = new GameUpdateDTO(null, null, -10L);

        AlexthequeStandardError thrownError = assertThrows(AlexthequeStandardError.class,
                () -> validationService.validateGameUpdate(gameUpdateDTO));

        assertThat(thrownError.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownError.getComment()).isEqualTo("Game time should be superior to 0.");
    }

    @Test
    @DisplayName("Update Game : With end date in future")
    void whenValidateUpdateDTO_givenDTOWithEndDateInFuture_thenExceptionIsThrown() {
        GameUpdateDTO gameUpdateDTO = new GameUpdateDTO(now, now.plusDays(10), null);

        AlexthequeStandardError thrownError = assertThrows(AlexthequeStandardError.class,
                () -> validationService.validateGameUpdate(gameUpdateDTO));

        assertThat(thrownError.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownError.getComment()).isEqualTo("The end date should be before or today.");
    }
}
