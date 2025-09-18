package com.axoulotl.alextheque.model.dto.input;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class GameDTO {
    @NotBlank(message = "The game name should not be null or empty")
    @Size(min = 1, max = 255, message = "The game name should be between 1 and 255 characters")
    private String name;

    @Positive
    @Min(value = 1, message = "The console Id should be greater than 0")
    @NotNull(message = "The console Id should not be null")
    private Integer console;

    @NotNull(message = "The inbox value should not be null")
    private Boolean inbox;
}
