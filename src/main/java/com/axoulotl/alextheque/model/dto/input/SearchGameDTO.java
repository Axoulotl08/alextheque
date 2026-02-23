package com.axoulotl.alextheque.model.dto.input;

import com.axoulotl.alextheque.service.validation.ValidDate;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@NotNull(message = "The search criteria should not be null")
public class SearchGameDTO {

    @Min(value = 1, message = "Console Id should be greater than 0")
    @Positive
    Integer consoleId;

    @Size(min = 1, max = 255, message = "The game name should be between 1 and 255 characters")
    String name;


    LocalDate startedAfter;

    @PositiveOrZero(message = "The status Id should be equal or greater than 0")
    Integer statusId;
}
