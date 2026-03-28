package com.axoulotl.alextheque.services.validation;

import com.axoulotl.alextheque.model.dto.input.GameUpdateDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GameUpdateDTO Validator Test")
public class GameUpdateDtoValidationTest {
    private static Validator validator;

    private static final LocalDate startDate = LocalDate.now().minusMonths(1);
    private static final LocalDate endDate = LocalDate.now().minusDays(7);
    private static final Long gameTime = 100L;
    private static final Long negativeGameTime = -100L;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("GameUpdateDTO Validator - All fields are ok")
    void whenAllFiledsAreValid_thenNoViolations() {
        GameUpdateDTO gameUpdateDTO = new GameUpdateDTO(startDate, endDate, gameTime);

        Set<ConstraintViolation<GameUpdateDTO>> violations = validator.validate(gameUpdateDTO);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("GameUpdateDTO Validator - All fields are null")
    void whenAllFieldsAreNull_thenViolations() {
        GameUpdateDTO gameUpdateDTO = new GameUpdateDTO(null, null, null);

        Set<ConstraintViolation<GameUpdateDTO>> violations = validator.validate(gameUpdateDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("At least one fields must be specified");
    }

    @Test
    @DisplayName("GameUpdateDTO Validator - Game time is negative")
    void whenGameTimeIsNegative_thenViolations() {
        GameUpdateDTO gameUpdateDTO = new GameUpdateDTO(startDate, endDate, negativeGameTime);

        Set<ConstraintViolation<GameUpdateDTO>> violations = validator.validate(gameUpdateDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("Game time should be greater than 0");
    }

    @Test
    @DisplayName("GameUpdateDTO Validator - End date but no start date")
    void whenEndDateIsNotEmptyButStartDateIs_thenViolations() {
        GameUpdateDTO gameUpdateDTO = new GameUpdateDTO(null, endDate, gameTime);

        Set<ConstraintViolation<GameUpdateDTO>> violations = validator.validate(gameUpdateDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("Start date must be filed if an end date is specified");
    }

    @Test
    @DisplayName("GameUpdateDTO - End date is before start date")
    void whenEndDateIsAfterStartDate_thenViolations(){
        GameUpdateDTO gameUpdateDTO = new GameUpdateDTO(endDate, startDate, gameTime);

        Set<ConstraintViolation<GameUpdateDTO>> violations = validator.validate(gameUpdateDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("End date must be after start date");
    }
}
