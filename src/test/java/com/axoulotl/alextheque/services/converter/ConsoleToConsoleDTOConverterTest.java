package com.axoulotl.alextheque.services.converter;

import com.axoulotl.alextheque.model.dto.output.ConsoleOutputDTO;
import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.service.converter.ConsoleToConsoleDTOConverter;
import com.axoulotl.alextheque.services.utils.UtilsTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ConsoleToConsoleDTOConverterTest {
    ConsoleToConsoleDTOConverter converter;

    @BeforeEach
    void setUp(){
        this.converter = new ConsoleToConsoleDTOConverter();
    }

    @Test
    void whenAddConsole_GivenDTO_ConvertedToConsoleOutputDTO(){
        Console console = UtilsTest.createConsole(1);

        ConsoleOutputDTO consoleOutputDTO = converter.convertConsoleToConsoleOutputDTO(console);

        assertThat(consoleOutputDTO).isNotNull();
        assertThat(consoleOutputDTO.getId()).isEqualTo(console.getId());
        assertThat(consoleOutputDTO.getName()).isEqualTo(console.getName());
        assertThat(consoleOutputDTO.getLaunchDate()).isEqualTo(console.getLaunchDate());
        assertThat(consoleOutputDTO.getManufacturer()).isEqualTo(console.getManufacturer());
        assertThat(consoleOutputDTO.getZone()).isEqualTo(console.getZone());
    }
}
