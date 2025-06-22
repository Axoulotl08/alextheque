package com.axoulotl.alextheque.service.validation;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.model.dto.input.ConsoleDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
     * Should not be null or empty
     * @param consoleDTO - ConsoleDTO to validate
     * @throws AlexthequeStandardError if the zone is incorrect
     */
    private void validateConsoleZone(ConsoleDTO consoleDTO) {
    }

    /**
     * Validate the launch date
     * @param consoleDTO - ConsoleDTO to validate
     * @throws AlexthequeStandardError if the launch date is incorrect
     */
    private void validateConsoleLaunchDate(ConsoleDTO consoleDTO) {
    }

    /**
     * Validate the manufacturer
     * @param consoleDTO - ConsoleDTO to validate
     * @throws AlexthequeStandardError if the manufacturer is incorrect
     */
    private void validateConsoleManufacturer(ConsoleDTO consoleDTO) {
    }

    /**
     * Validate the name
     * @param consoleDTO - ConsoleDTO to validate
     * @throws AlexthequeStandardError if the name is incorrect
     */
    private void validateConsoleName(ConsoleDTO consoleDTO) {
    }
}
