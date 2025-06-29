package com.axoulotl.alextheque.service.validation;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.ConsoleDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConsoleValidationService {
    /**
     * Validate the entry DTO before conversion and save into DB
     * @param consoleDTO  - ConsoleDTO to validate
     * @throws AlexthequeStandardError if any field isn't good
     */
    public void validateConsoleInsert(ConsoleDTO consoleDTO) throws AlexthequeStandardError{
        validateConsoleName(consoleDTO);
        validateConsoleManufacturer(consoleDTO);
        validateConsoleLaunchDate(consoleDTO);
        validateConsoleZone(consoleDTO);
    }

    /**
     * Validate the zone
     * Should be between 1 and 3
     * @param consoleDTO - ConsoleDTO to validate
     * @throws AlexthequeStandardError if the zone is incorrect
     */
    private void validateConsoleZone(ConsoleDTO consoleDTO) throws AlexthequeStandardError {
        if(consoleDTO.getZone() < 1 || consoleDTO.getZone() > 3){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "The Zone is incorrect. Should be between 1, 2 or 3.");
        }
    }

    /**
     * Validate the launch date
     * Should be before today's date and after 1980.
     * @param consoleDTO - ConsoleDTO to validate
     * @throws AlexthequeStandardError if the launch date is incorrect
     */
    private void validateConsoleLaunchDate(ConsoleDTO consoleDTO) throws AlexthequeStandardError {
        if(consoleDTO.getLaunchDate().isAfter(LocalDateTime.now())){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "The launch date should be before now.");
        }

        if(consoleDTO.getLaunchDate().isBefore(LocalDateTime.of(1980, 1, 1, 1, 1))){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "The launch date should be after 01-01-1980.");
        }
    }

    /**
     * Validate the manufacturer
     * Must no be blank or empty
     * @param consoleDTO - ConsoleDTO to validate
     * @throws AlexthequeStandardError if the manufacturer is incorrect
     */
    private void validateConsoleManufacturer(ConsoleDTO consoleDTO) throws AlexthequeStandardError {
        if(StringUtils.isBlank(consoleDTO.getManufacturer())){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "The manufacturer should not be empty of null.");
        }
    }

    /**
     * Validate the name
     * @param consoleDTO - ConsoleDTO to validate
     * @throws AlexthequeStandardError if the name is incorrect
     */
    private void validateConsoleName(ConsoleDTO consoleDTO) throws AlexthequeStandardError {
        if(StringUtils.isBlank(consoleDTO.getName())){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "The name should not be empty of null.");
        }
    }
}
