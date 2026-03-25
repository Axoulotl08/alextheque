package com.axoulotl.alextheque.model.dto.input;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public record GameDTO (
    @NotBlank(message = "The game name should not be null or empty")
    @Size(max = 255, message = "The game name should be between 1 and 255 characters")
    String name,

    @Min(value = 1, message = "The console id should be greater than 0")
    @NotNull(message = "The console id should not be null")
    Integer console,

    @NotNull(message = "The inbox value should not be null")
    Boolean inbox){
}
