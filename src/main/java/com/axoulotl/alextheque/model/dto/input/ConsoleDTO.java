package com.axoulotl.alextheque.model.dto.input;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record ConsoleDTO(

    @NotBlank(message = "The console name should not be null")
    @Size(max = 255, message = "The console name should be between 1 and 255 characters")
    String name,

    @NotBlank(message = "The console manufacturer should not be null")
    @Size(max = 100, message = "The console manufacturer should be between 1 and 100 characters")
    String manufacturer,

    @PastOrPresent(message = "The console launch date should not be in the future")
    @NotNull(message = "The console launch date should not be null")
    LocalDateTime launchDate,

    @NotNull(message = "The console zone should not be null")
    @Min(value = 1, message = "The zone should be between 1 and 3")
    @Max(value = 3, message = "The zone should be between 1 and 3")
    Integer zone
){
}
