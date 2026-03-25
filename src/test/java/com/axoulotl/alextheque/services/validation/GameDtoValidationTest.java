package com.axoulotl.alextheque.services.validation;

import com.axoulotl.alextheque.model.dto.input.GameDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GameDTO Validator Tests")
public class GameDtoValidationTest {
    private static Validator validator;

    @BeforeAll
    static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("GameDTO Validator - All fields are ok")
    void whenAllFieldsValid_thenNoViolations() {
        GameDTO gameDTO = new GameDTO("Valid Name", 1, Boolean.TRUE);

        Set<ConstraintViolation<GameDTO>> violations = validator.validate(gameDTO);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("GameDTO Validator - Blank Name")
    void whenNameIsBlank_thenViolations() {
        GameDTO gameDTO = new GameDTO("", 1, Boolean.TRUE);

        Set<ConstraintViolation<GameDTO>> violations = validator.validate(gameDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("The game name should not be null or empty");
    }

    @Test
    @DisplayName("GameDTO Validator - Null Name")
    void whenNameIsNull_thenViolations() {
        GameDTO gameDTO = new GameDTO(null, 1, Boolean.TRUE);

        Set<ConstraintViolation<GameDTO>> violations = validator.validate(gameDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("The game name should not be null or empty");
    }

    @Test
    @DisplayName("GameDTO Validator - Name Too Long")
    void whenNameTooLong_thenViolations() {
        GameDTO gameDTO = new GameDTO("A".repeat(260), 1, Boolean.FALSE);

        Set<ConstraintViolation<GameDTO>> violations = validator.validate(gameDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("The game name should be between 1 and 255 characters");
    }

    @Test
    @DisplayName("GameDTO Validator - ConsoleId is null")
    void whenConsoleIdIsNull_thenViolations() {
        GameDTO gameDTO = new GameDTO("Valid Name", null, Boolean.TRUE);

        Set<ConstraintViolation<GameDTO>> violations = validator.validate(gameDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("The console id should not be null");
    }

    @Test
    @DisplayName("GameDTO Validator - ConsoleId is negative")
    void whenConsoleIdIsNegative_thenViolations() {
        GameDTO gameDTO = new GameDTO("Valid Name", -1, Boolean.TRUE);

        Set<ConstraintViolation<GameDTO>> violations = validator.validate(gameDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("The console id should be greater than 0");
    }

    @Test
    @DisplayName("GameDTO Validator - InBox is null")
    void whenInBoxIsNull_thenViolations() {
        GameDTO gameDTO = new GameDTO("Valid Name", 1, null);

        Set<ConstraintViolation<GameDTO>> violations = validator.validate(gameDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("The inbox value should not be null");
    }


}
