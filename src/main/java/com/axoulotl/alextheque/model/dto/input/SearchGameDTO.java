package com.axoulotl.alextheque.model.dto.input;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record SearchGameDTO(

    @Positive(message = "Console Id should be positive")
    Integer consoleId,

    @Size(min = 2, max = 255, message = "Name should be between 2 and 255 characters")
    String name,

    @PastOrPresent(message = "Start date should not be in the future")
    LocalDate startedAfter,

    @Positive(message = "Status should be positive")
    @Max(value = 4, message = "Status should be between 1 and 4")
    Integer statusId){
}
