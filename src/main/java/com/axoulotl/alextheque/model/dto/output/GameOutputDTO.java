package com.axoulotl.alextheque.model.dto.output;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class GameOutputDTO {
    private Integer id;
    private String name;
    private Boolean inbox;
    private ConsoleOutputDTO console;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long gameTime;
}
