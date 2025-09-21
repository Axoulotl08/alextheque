package com.axoulotl.alextheque.model.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ConsoleDTO {

    @NotBlank(message = "The console name should no be null")
    @Size(min = 1, max = 255, message = "The console name should between 1 and 255 characters")
    private String name;

    @NotBlank(message = "The console manufacturer should no be null")
    @Size(min = 1, max = 255, message = "The console manufacturer should between 1 and 255 characters")
    private String manufacturer;

    @NotNull(message = "The console launch date should not be null")
    private LocalDateTime launchDate;

    @NotNull(message = "The console zone should not be null")
    private Integer zone;
}
