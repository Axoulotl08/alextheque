package com.axoulotl.alextheque.model.dto.output;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ConsoleOutputDTO {
    private String name;
    private String manufacturer;
    private LocalDateTime launchDate;
}
