package com.axoulotl.alextheque.service;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.model.dto.input.ConsoleDTO;
import com.axoulotl.alextheque.model.dto.output.ConsoleOutputDTO;
import com.axoulotl.alextheque.model.entity.Console;

import java.util.List;

public interface ConsoleService {
    /**
     * Create and save a new console into the database.
     * <p>
     *     Validate data entry (name, manufacturer, ...) before the creation
     * </p>
     *
     * @param consoleDTO - ConsoleDTO with all the data to create (name, manufacturer, area and launch date)
     * @return the craated console with it's unique id
     * @throws AlexthequeStandardError in case of an error during the save into DB
     *
     * @see Console
     * @see ConsoleDTO
     */
    Console addConsole(ConsoleDTO consoleDTO) throws AlexthequeStandardError;

    /**
     * Get all the console saved in the database
     * <p>
     *     Consoles are returned in a DTO which includes all the informations
     *     of the consoles.
     * </p>
     *
     * @return -  all the console that are saved into the database. Or an empty list is there are no console
     *
     * @see ConsoleOutputDTO
     */
    List<ConsoleOutputDTO> getAllConsole();
}
