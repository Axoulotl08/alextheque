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
}
