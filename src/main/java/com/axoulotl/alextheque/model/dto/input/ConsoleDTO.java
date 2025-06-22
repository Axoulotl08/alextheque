package com.axoulotl.alextheque.model.dto.input;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ConsoleDTO {
    private String name;
    private String manufacturer;
    private LocalDateTime launchDate;
    private Integer zone;
}
