package com.axoulotl.alextheque.model.dto.output;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GameOutputDTO {
    private Integer id;
    private String name;
    private Boolean inbox;
    private ConsoleOutputDTO console;
}
