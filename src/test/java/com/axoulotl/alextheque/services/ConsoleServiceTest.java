package com.axoulotl.alextheque.services;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.ConsoleDTO;
import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.model.entity.enums.Zone;
import com.axoulotl.alextheque.repository.ConsoleRepository;
import com.axoulotl.alextheque.service.ConsoleService;
import com.axoulotl.alextheque.service.validation.ConsoleValidationService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsoleServiceTest {

    @Mock
    private ConsoleRepository consoleRepository;

    @Mock
    private ConsoleValidationService consoleValidationService;

    @InjectMocks
    private ConsoleService consoleService;

    @Test
    public void whenAddConsole_GivenGoodDTO_thenConsoleIsSaved() throws AlexthequeStandardError {
        LocalDateTime nowMinusOneHour = LocalDateTime.now().minusHours(1L).truncatedTo(ChronoUnit.MILLIS);

        ConsoleDTO consoleDTO = new ConsoleDTO();
        consoleDTO.setManufacturer("Manuf");
        consoleDTO.setName("Name");
        consoleDTO.setZone(1);
        consoleDTO.setLaunchDate(nowMinusOneHour);

        Console console = new Console();
        console.setId(1);
        console.setName("Name");
        console.setManufacturer("Manuf");
        console.setZone(Zone.JAP);
        console.setLaunchDate(nowMinusOneHour);

        when(consoleRepository.save(any(Console.class))).thenReturn(console);

        Console result = consoleService.addConsole(consoleDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Name");
        assertThat(result.getManufacturer()).isEqualTo("Manuf");
        assertThat(result.getZone()).isEqualTo(Zone.JAP);
        assertThat(result.getLaunchDate()).isEqualTo(nowMinusOneHour);

        verify(consoleRepository, times(1)).save(any(Console.class));
    }

    @Test
    public void whenAddConsole_GivenDTOWithWrongDTO_thenExceptionIsThrowed() throws AlexthequeStandardError {
        LocalDateTime nowMinusOneHour = LocalDateTime.now().minusHours(1L).truncatedTo(ChronoUnit.MILLIS);

        ConsoleDTO consoleDTO = new ConsoleDTO();
        consoleDTO.setManufacturer("Manuf");
        consoleDTO.setName("");
        consoleDTO.setZone(1);
        consoleDTO.setLaunchDate(nowMinusOneHour);

        doThrow(new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "Nom de console invalide"))
                .when(consoleValidationService).validateConsoleInsert(consoleDTO);

        AlexthequeStandardError thrownException = assertThrows(AlexthequeStandardError.class, () -> consoleService.addConsole(consoleDTO));

        verify(consoleRepository, never()).save(any(Console.class));
    }

    @Test
    public void whenAddConsole_GivenDTOButBDDError_thenExceptionIsThrowed() throws AlexthequeStandardError {
        LocalDateTime nowMinusOneHour = LocalDateTime.now().minusHours(1L).truncatedTo(ChronoUnit.MILLIS);

        ConsoleDTO consoleDTO = new ConsoleDTO();
        consoleDTO.setManufacturer("Manuf");
        consoleDTO.setName("");
        consoleDTO.setZone(1);
        consoleDTO.setLaunchDate(nowMinusOneHour);

        Console console = new Console();
        console.setId(1);
        console.setName("Name");
        console.setManufacturer("Manuf");
        console.setZone(Zone.JAP);
        console.setLaunchDate(nowMinusOneHour);


        doThrow(new RuntimeException("Simulated DB connection error"))
                .when(consoleRepository).save(any(Console.class));

        AlexthequeStandardError thrownException = assertThrows(AlexthequeStandardError.class, () -> consoleService.addConsole(consoleDTO));

        verify(consoleRepository, times(1)).save(any(Console.class));

        assertThat(thrownException.getError()).isEqualTo(StandardErrorEnum.ERROR_DATABASE);
        assertThat(thrownException.getComment()).contains("Something bad happen during save into DB.");
    }
}
