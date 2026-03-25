package com.axoulotl.alextheque.services.validation;

import com.axoulotl.alextheque.model.dto.input.ConsoleDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ConsoleDTO Validator Tests")
public class ConsoleDtoValidationTest {
    private static Validator validator;

    private static final LocalDateTime now = LocalDateTime.now().minusYears(1).truncatedTo(ChronoUnit.MILLIS);

    private static final Integer zoneId = 1;
    private static final Integer zoneIdTooLow = 0;
    private static final Integer zoneIdTooHigh = 4;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsValid_thenNoViolations() {
        ConsoleDTO consoleDTO = new ConsoleDTO("Test Console", "Manuf", now, zoneId);

        Set<ConstraintViolation<ConsoleDTO>> violations = validator.validate(consoleDTO);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenNameIsBlank_thenNoViolations() {
        ConsoleDTO consoleDTO = new ConsoleDTO("", "Manuf", now, zoneId);

        Set<ConstraintViolation<ConsoleDTO>> violations = validator.validate(consoleDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("The console name should not be null");
    }

    @Test
    void whenNameIsNull_thenNoViolations() {
        ConsoleDTO consoleDTO = new ConsoleDTO("", "Manuf", now, zoneId);

        Set<ConstraintViolation<ConsoleDTO>> violations = validator.validate(consoleDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("The console name should not be null");
    }

    @Test
    void whenNameIsTooLong_thenViolations() {
        ConsoleDTO consoleDTO = new ConsoleDTO("A".repeat(256), "Manuf", now, zoneId);

        Set<ConstraintViolation<ConsoleDTO>> violations = validator.validate(consoleDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("The console name should be between 1 and 255 characters");
    }

    @Test
    void whenManufacturerIsBlank_thenViolations() {
        ConsoleDTO consoleDTO = new ConsoleDTO("Test Console", "", now, zoneId);

        Set<ConstraintViolation<ConsoleDTO>> violations = validator.validate(consoleDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("The console manufacturer should not be null");
    }

    @Test
    void whenManufacturerIsNull_thenViolations() {
        ConsoleDTO consoleDTO = new ConsoleDTO("Test Console", null, now, zoneId);

        Set<ConstraintViolation<ConsoleDTO>> violations = validator.validate(consoleDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("The console manufacturer should not be null");

    }

    @Test
    void whenManufacturerIsTooLong_thenViolations() {
        ConsoleDTO consoleDTO = new ConsoleDTO("Test Console", "A".repeat(101), now, zoneId);

        Set<ConstraintViolation<ConsoleDTO>> violations = validator.validate(consoleDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("The console manufacturer should be between 1 and 100 characters");
    }

    @Test
    void whenZoneIsNull_thenViolations() {
        ConsoleDTO consoleDTO = new ConsoleDTO("Test Console", "Manuf", now, null);

        Set<ConstraintViolation<ConsoleDTO>> violations = validator.validate(consoleDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("The console zone should not be null");
    }

    @Test
    void whenZoneIsUnderOne_thenViolations() {
        ConsoleDTO consoleDTO = new ConsoleDTO("Test Console", "Manuf", now, zoneIdTooLow);

        Set<ConstraintViolation<ConsoleDTO>> violations = validator.validate(consoleDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("The zone should be between 1 and 3");
    }

    @Test
    void whenZoneIsAboveThree_thenViolations() {
        ConsoleDTO consoleDTO = new ConsoleDTO("Test Console", "Manuf", now, zoneIdTooHigh);

        Set<ConstraintViolation<ConsoleDTO>> violations = validator.validate(consoleDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("The zone should be between 1 and 3");
    }

    @Test
    void whenLaunchDateIsNull_thenViolations() {
        ConsoleDTO consoleDTO = new ConsoleDTO("Test Console", "Manuf", null, zoneId);

        Set<ConstraintViolation<ConsoleDTO>> violations = validator.validate(consoleDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("The console launch date should not be null");
    }


    @Test
    void whenLaunchDateIsInFuture_thenViolations() {
        ConsoleDTO consoleDTO = new ConsoleDTO("Test Console", "Manuf", now.plusYears(2), zoneId);

        Set<ConstraintViolation<ConsoleDTO>> violations = validator.validate(consoleDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("The console launch date should not be in the future");
    }

    @Test
    void whenMultipleFieldsInvalid_thenViolations() {
        ConsoleDTO consoleDTO = new ConsoleDTO("", "", now, null);

        Set<ConstraintViolation<ConsoleDTO>> violations = validator.validate(consoleDTO);

        assertThat(violations).hasSize(3);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder("The console zone should not be null",
                        "The console name should not be null",
                        "The console manufacturer should not be null");
    }
}
