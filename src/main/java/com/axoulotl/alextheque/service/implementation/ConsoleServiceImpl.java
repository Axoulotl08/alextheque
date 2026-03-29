package com.axoulotl.alextheque.service.implementation;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.ConsoleDTO;
import com.axoulotl.alextheque.model.dto.output.ConsoleOutputDTO;
import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.model.entity.enums.Zone;
import com.axoulotl.alextheque.repository.ConsoleRepository;
import com.axoulotl.alextheque.service.ConsoleService;
import com.axoulotl.alextheque.service.converter.ConsoleToConsoleDTOConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsoleServiceImpl implements ConsoleService {

    private final ConsoleRepository consoleRepository;
    private final ConsoleToConsoleDTOConverter converter;


    @Override
    public Console addConsole(ConsoleDTO consoleDTO) throws AlexthequeStandardError {
        Console console = Console.builder()
                .name(consoleDTO.name())
                .manufacturer(consoleDTO.manufacturer())
                .zone(Zone.getZoneFromInt(consoleDTO.zone()))
                .launchDate(consoleDTO.launchDate()).build();

        Console valReturn;

        try{
            valReturn = consoleRepository.save(console);
        } catch (Exception e){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_DATABASE, "Something bad happen during save into DB.");
        }
        return valReturn;
    }


    @Override
    public List<ConsoleOutputDTO> getAllConsole(){
        return consoleRepository.findAll().stream().map(converter::convertConsoleToConsoleOutputDTO).toList();
    }
}
