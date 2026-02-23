package com.axoulotl.alextheque.model.dto.input;

import com.axoulotl.alextheque.service.validation.ValidDate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameUpdateDTO {

    @ValidDate
    private LocalDate startDate;

    @ValidDate
    private LocalDate endDate;

    @Min(value = 1, message = "Game time should be greater than 0")
    private Long gameTime;
}
