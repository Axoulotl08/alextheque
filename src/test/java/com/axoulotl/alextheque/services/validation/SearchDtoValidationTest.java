package com.axoulotl.alextheque.services.validation;


import com.axoulotl.alextheque.model.dto.input.SearchGameDTO;
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

@DisplayName("SearchDTO Validator Test")
public class SearchDtoValidationTest {
    private static Validator validator;

    private static final LocalDate pastDate = LocalDate.now().minusYears(1);
    private static final LocalDate futureDate = LocalDate.now().plusYears(1);
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
    @DisplayName("SearchDTO Validator - All fields are ok")
    void whenAllFieldsAreValid_thenNoViolations() {
        SearchGameDTO searchGameDTO = new SearchGameDTO(validConsoleId, "Name", pastDate, validStatus);

        Set<ConstraintViolation<SearchGameDTO>> violations = validator.validate(searchGameDTO);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("SearchDTO Validator - Name is too short")
    void whenNameIsTooShort_thenViolations() {
        SearchGameDTO searchGameDTO = new SearchGameDTO(validConsoleId, "N", pastDate, validStatus);

        Set<ConstraintViolation<SearchGameDTO>> violations = validator.validate(searchGameDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("Name should be between 2 and 255 characters");
    }

    @Test
    @DisplayName("SearchDTO Validator - Name is too long")
    void whenNameIsTooLong_thenViolations() {
        SearchGameDTO searchGameDTO = new SearchGameDTO(validConsoleId, "A".repeat(260), pastDate, validStatus);

        Set<ConstraintViolation<SearchGameDTO>> violations = validator.validate(searchGameDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("Name should be between 2 and 255 characters");
    }

    @Test
    @DisplayName("SearchDTO Validator -  ConsoleId is invalid")
    void whenConsoleIdIsZero_thenViolations() {
        SearchGameDTO searchGameDTO = new SearchGameDTO(invalidConsoleId, "Test", pastDate, validStatus);

        Set<ConstraintViolation<SearchGameDTO>> violations = validator.validate(searchGameDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("Console Id should be positive");
    }

    @Test
    @DisplayName("SearchDTO Validator - StartedAfter is in future")
    void whenStartedAfterIsFuture_thenViolations() {
        SearchGameDTO searchGameDTO = new SearchGameDTO(validConsoleId, "Name", futureDate, validStatus);

        Set<ConstraintViolation<SearchGameDTO>> violations = validator.validate(searchGameDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("Start date should not be in the future");
    }

    @Test
    @DisplayName("SearchDTO Validator - StatusId is negative")
    void whenStatusIdIsNegative_thenViolations() {
        SearchGameDTO searchGameDTO = new SearchGameDTO(validConsoleId, "Name", pastDate, validStatus * -1);

        Set<ConstraintViolation<SearchGameDTO>> violations = validator.validate(searchGameDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("Status should be between 1 and 4");
    }

    @Test
    @DisplayName("SearchDTO Validator - StatusId is above 4")
    void whenStatusIdIsGreaterThan4_thenViolations() {
        SearchGameDTO searchGameDTO = new SearchGameDTO(validConsoleId, "Name", pastDate, invalidStatus);

        Set<ConstraintViolation<SearchGameDTO>> violations = validator.validate(searchGameDTO);

        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsOnly("Status should be between 1 and 4");
    }

    @Test
    @DisplayName("SearchDTO Validator - Multiple fields are invalid")
    void whenMultipleFieldsAreInvalid_thenViolations() {
        SearchGameDTO searchGameDTO = new SearchGameDTO(invalidConsoleId, "", pastDate, validStatus);

        Set<ConstraintViolation<SearchGameDTO>> violations = validator.validate(searchGameDTO);

        assertThat(violations).hasSize(2);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder("Console Id should be positive", "Name should be between 2 and 255 characters");
    }
}
