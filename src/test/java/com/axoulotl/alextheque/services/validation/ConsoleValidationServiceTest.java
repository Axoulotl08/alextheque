package com.axoulotl.alextheque.services.validation;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.ConsoleDTO;
import com.axoulotl.alextheque.validation.ConsoleValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Deprecated
@Disabled("Old test. Replaced by validator test.")
public class ConsoleValidationServiceTest {
    private ConsoleValidationService consoleValidationService;

    private static final LocalDateTime now = LocalDateTime.now().minusYears(1).truncatedTo(ChronoUnit.MILLIS);

    @BeforeEach
    void setUp() {
        this.consoleValidationService = new ConsoleValidationService();
    }

    @Test
    void whenValidateConsoleInsert_givenValidConsoleDTO_thenNoExceptionThrown() throws AlexthequeStandardError {
        ConsoleDTO consoleDTO = new ConsoleDTO("Valid Console Name", "Valid Manuf", now, 1);

        consoleValidationService.validateConsoleInsert(consoleDTO);
    }

    @Test
    void whenValidateConsoleInsert_givenConsoleDTOWithEmptyName_thenAlexthequeStandardErrorIsThrown() {
        // GIVEN
        ConsoleDTO consoleDTO = new ConsoleDTO("", "Valid Manuf", now, 1);

        AlexthequeStandardError thrownException = assertThrows(AlexthequeStandardError.class, () -> consoleValidationService.validateConsoleInsert(consoleDTO));

        assertThat(thrownException.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownException.getComment()).contains("The name should not be empty of null.");

    }

    @Test
    void whenValidateConsoleInsert_givenConsoleDTOWithNullName_thenAlexthequeStandardErrorIsThrown() {
        ConsoleDTO consoleDTO = new ConsoleDTO(null, "Valid Manuf", now, 1);


        AlexthequeStandardError thrownException = assertThrows(AlexthequeStandardError.class, () -> {
            consoleValidationService.validateConsoleInsert(consoleDTO);
        });

        assertThat(thrownException.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownException.getComment()).contains("The name should not be empty of null.");
    }

    @Test
    void whenValidateConsoleInsert_givenConsoleDTOWithNullManufacturer_thenAlexthequeStandardErrorIsThrow() {
        ConsoleDTO consoleDTO = new ConsoleDTO("Valid Console Name", null, now, 1);

        AlexthequeStandardError thrownException = assertThrows(AlexthequeStandardError.class, () -> {
            consoleValidationService.validateConsoleInsert(consoleDTO);
        });

        assertThat(thrownException.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownException.getComment()).contains("The manufacturer should not be empty of null.");
    }

    @Test
    void whenValidateConsoleInsert_givenConsoleDTOWithEmptyManufacturer_thenAlexthequeStandardErrorIsThrow() {
        ConsoleDTO consoleDTO = new ConsoleDTO("Valid Console Name", "", now, 1);

        AlexthequeStandardError thrownException = assertThrows(AlexthequeStandardError.class, () -> {
            consoleValidationService.validateConsoleInsert(consoleDTO);
        });

        assertThat(thrownException.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownException.getComment()).contains("The manufacturer should not be empty of null.");
    }

    @Test
    void whenValidateConsoleInsert_givenConsoleDTOWithZoneIdNegative_thenAlexthequeStandardErrorIsThrow() {
        ConsoleDTO consoleDTO = new ConsoleDTO("Valid Console Name", "Manuf", now, -1);

        AlexthequeStandardError thrownException = assertThrows(AlexthequeStandardError.class, () -> {
            consoleValidationService.validateConsoleInsert(consoleDTO);
        });

        assertThat(thrownException.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownException.getComment()).contains("The Zone is incorrect. Should be between 1, 2 or 3.");
    }

    @Test
    void whenValidateConsoleInsert_givenConsoleDTOWithZoneIdOverThree_thenAlexthequeStandardErrorIsThrow() {
        ConsoleDTO consoleDTO = new ConsoleDTO("Valid Console Name", "Manuf", now, 445);

        AlexthequeStandardError thrownException = assertThrows(AlexthequeStandardError.class, () -> {
            consoleValidationService.validateConsoleInsert(consoleDTO);
        });

        assertThat(thrownException.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownException.getComment()).contains("The Zone is incorrect. Should be between 1, 2 or 3.");
    }

    @Test
    void whenValidateConsoleInsert_givenConsoleDTOWithLaunchDateInFuture_thenAlexthequeStandardErrorIsThrow() {
        ConsoleDTO consoleDTO = new ConsoleDTO("Valid Console Name", "Manuf", LocalDateTime.now().plusYears(1).truncatedTo(ChronoUnit.MILLIS), 1);

        AlexthequeStandardError thrownException = assertThrows(AlexthequeStandardError.class, () -> {
            consoleValidationService.validateConsoleInsert(consoleDTO);
        });

        assertThat(thrownException.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownException.getComment()).contains("The launch date should be before now.");
    }

    @Test
    void whenValidateConsoleInsert_givenConsoleDTOWithLaunchDateBefore1800_thenAlexthequeStandardErrorIsThrow() {
        ConsoleDTO consoleDTO = new ConsoleDTO("Valid Console Name", "Manuf", LocalDateTime.now().minusYears(50).truncatedTo(ChronoUnit.MILLIS), 1);

        AlexthequeStandardError thrownException = assertThrows(AlexthequeStandardError.class, () -> {
            consoleValidationService.validateConsoleInsert(consoleDTO);
        });

        assertThat(thrownException.getError()).isEqualTo(StandardErrorEnum.ERROR_INPUT);
        assertThat(thrownException.getComment()).contains("The launch date should be after 01-01-1980.");
    }
}
