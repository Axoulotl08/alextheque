package com.axoulotl.alextheque.service.converter;

import com.axoulotl.alextheque.model.dto.output.ConsoleOutputDTO;
import com.axoulotl.alextheque.model.entity.Console;
import org.springframework.stereotype.Service;

@Service
public class ConsoleToConsoleDTOConverter {

    public ConsoleOutputDTO convertConsoleToConsoleOutputDTO(Console console) {

    return ConsoleOutputDTO.builder()
            .name(console.getName())
            .id(console.getId())
            .manufacturer(console.getManufacturer())
            .zone(console.getZone())
            .launchDate(console.getLaunchDate())
            .creationDate(console.getCreationDate())
            .build();
    }
}
