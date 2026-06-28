package com.axoulotl.alextheque.model.dto.input;

import com.axoulotl.alextheque.validation.annotation.AtLeastOneField;
import com.axoulotl.alextheque.validation.annotation.ValidDateRange;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@ValidDateRange(start = "startDate", end = "endDate")
@AtLeastOneField(
        fields = {"startDate", "endDate", "gameTime"},
        message = "At least one fields must be specified"
)
public record GameUpdateDTO(
        LocalDate startDate,

        LocalDate endDate,

        @Positive(message = "Game time should be greater than 0")
        Long gameTime) {
}
