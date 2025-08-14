package com.axoulotl.alextheque.model.dto.output;

import com.axoulotl.alextheque.model.entity.enums.Status;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Builder
public class GameOutputDTO {
    private Integer id;
    private String name;
    private Boolean inbox;
    private ConsoleOutputDTO console;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long gameTime;
    private Status status;
}
