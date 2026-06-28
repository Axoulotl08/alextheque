package com.axoulotl.alextheque.services;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.ConsoleDTO;
import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.model.entity.enums.Zone;
import com.axoulotl.alextheque.repository.ConsoleRepository;
import com.axoulotl.alextheque.service.ConsoleService;
import com.axoulotl.alextheque.service.implementation.ConsoleServiceImpl;
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

    @InjectMocks
    private ConsoleServiceImpl consoleService;

    private static final LocalDateTime nowMinusOneHour = LocalDateTime.now().minusHours(1L).truncatedTo(ChronoUnit.MILLIS);

    @Test
    public void whenAddConsole_GivenGoodDTO_thenConsoleIsSaved() throws AlexthequeStandardError {


        ConsoleDTO consoleDTO = new ConsoleDTO("Name", "Manuf", nowMinusOneHour, 1);

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
    public void whenAddConsole_GivenDTOButBDDError_thenExceptionIsThrowed() throws AlexthequeStandardError {

        ConsoleDTO consoleDTO = new ConsoleDTO("", "Manuf", nowMinusOneHour, 1);;

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
