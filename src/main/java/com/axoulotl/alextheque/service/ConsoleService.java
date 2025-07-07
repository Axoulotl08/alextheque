package com.axoulotl.alextheque.service;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.ConsoleDTO;
import com.axoulotl.alextheque.model.dto.output.Consoles;
import com.axoulotl.alextheque.model.entity.Console;
import com.axoulotl.alextheque.model.entity.enums.Zone;
import com.axoulotl.alextheque.repository.ConsoleRepository;
import com.axoulotl.alextheque.service.validation.ConsoleValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsoleService {

    ConsoleRepository consoleRepository;
    ConsoleValidationService consoleValidationService;

    @Autowired
    public ConsoleService(ConsoleRepository consoleRepository,
                          ConsoleValidationService consoleValidationService){
        this.consoleRepository = consoleRepository;
        this.consoleValidationService = consoleValidationService;
    }

    /**
     * Add console into DB
     *
     * @param consoleDTO - ConsoleDTO
     * @return the console created and saved into DB
     * @throws AlexthequeStandardError in case of bad input or error during the save into DB
     */
    public Console addConsole(ConsoleDTO consoleDTO) throws AlexthequeStandardError {
        consoleValidationService.validateConsoleInsert(consoleDTO);

        Console console = Console.builder()
                .name(consoleDTO.getName())
                .manufacturer(consoleDTO.getManufacturer())
                .zone(Zone.getZoneFromInt(consoleDTO.getZone()))
                .launchDate(consoleDTO.getLaunchDate()).build();

        Console valReturn;

        try{
            valReturn = consoleRepository.save(console);
        } catch (Exception e ){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_DATABASE, "Something bad happen during save into DB.");
        }
        return valReturn;
    }

    /**
     * Get all the console in DB and return it.
     *
     * @return all the console that are saved into DB.
     */
    public List<Console> getAllConsole(){

        return consoleRepository.findAll();
    }
}
