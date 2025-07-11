package com.axoulotl.alextheque.model.dto.output;

import com.axoulotl.alextheque.model.entity.enums.Zone;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ConsoleOutputDTO {
    private Integer id;
    private String name;
    private String manufacturer;
    private LocalDateTime launchDate;
    private Zone zone;
    private LocalDateTime creationDate;
}
