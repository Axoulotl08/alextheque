package com.axoulotl.alextheque.model.dto.input;

import com.axoulotl.alextheque.service.validation.ValidDateRange;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

@ValidDateRange(start = "startDate", end = "endDate")
public record GameUpdateDTO(
        LocalDate startDate,

        LocalDate endDate,

        @Min(value = 1, message = "Game time should be greater than 0")
        Long gameTime) {
}
