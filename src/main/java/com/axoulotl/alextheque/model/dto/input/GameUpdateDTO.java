package com.axoulotl.alextheque.model.dto.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameUpdateDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private Long gameTime;
}
