package com.axoulotl.alextheque.model.dto.input;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchGameDTO {

    @Positive(message = "L'ID de la console doit être positif")
    Integer consoleId;

    @Size(min = 2, max = 255, message = "Le nom doit contenir entre 2 et 255 caractères")
    String name;

    @PastOrPresent(message = "La date de départ ne doit pas être dans le futur")
    LocalDate startedAfter;

    @Positive(message = "Le status doit être positif")
    @Max(value = 4, message = "Le status ne doit pas être plus grand que 4")
    Integer statusId;
}
