package com.axoulotl.alextheque.services.validation;


import com.axoulotl.alextheque.model.dto.input.SearchGameDTO;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SearchDTO Validator Test")
public class SearchDtoValidationTest {
    private static Validator validator;

    private static final LocalDate date = LocalDate.now().minusYears(1);
    private static final Integer validConsoleId = 1;
    private static final Integer invalidConsoleId = -1;
    private static final Integer validStatus = 2;
    private static final Integer invalidStatus = 5;

    @BeforeAll
    static void beforeAll() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("SearchDto Validator - All fields are ok")
    void whenAllFieldsAreValid_thenNoViolations() {
        SearchGameDTO searchGameDTO = new SearchGameDTO(validConsoleId, "Name", date, validStatus);
    }
}
