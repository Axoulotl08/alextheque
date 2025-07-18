package com.axoulotl.alextheque.service.validation;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.dto.input.GameUpdateDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GameValidationService {
    public void validateGameInsert(GameDTO gameDTO) throws AlexthequeStandardError {
        validateGameName(gameDTO);
        validateGameConsole(gameDTO);
    }

    /**
     * Validate the gameId console
     * Should be greater than 0.
     * Check is console existe is made in service
     * @param gameDTO - GameDTO to validate the console
     */
    private void validateGameConsole(GameDTO gameDTO) throws AlexthequeStandardError {
        if(gameDTO.getConsole() <= 0){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "Console Id should be greater than 0");
        }
    }

    /**
     * Validate the name of the GameDTO
     * Should not be null or empty
     * @param gameDTO - GameDTO to validate the name
     * @throws AlexthequeStandardError if the name is empty or null
     */
    private void validateGameName(GameDTO gameDTO) throws AlexthequeStandardError {
        if(StringUtils.isBlank(gameDTO.getName())) {
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "Le nom ne doit pas être nul");
        }
    }

    /**
     * Validate a update DTO for a game
     *
     * @param gameUpdateDTO - the gameUpdateDTO to validate
     * @throws AlexthequeStandardError if any field is not validate
     */
    public void validateGameUpdate(GameUpdateDTO gameUpdateDTO) throws AlexthequeStandardError {
        if(gameUpdateDTO.getGameTime() != null) validateGameTime(gameUpdateDTO);
        validateDate(gameUpdateDTO);
    }

    /**
     * Validate the gameTime
     * Should not be inferior to 0
     * @param gameUpdateDTO - the gameUpdateDTO to validate
     * @throws AlexthequeStandardError if the game time is < 0
     */
    private void validateGameTime(GameUpdateDTO gameUpdateDTO) throws AlexthequeStandardError {
        if(gameUpdateDTO.getGameTime() < 0){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "Game time should be superior to 0.");
        }
    }

    /**
     * Validate the date
     * @param gameUpdateDTO - the gameUpdateDTO to validate
     * @throws AlexthequeStandardError if there is an error in a date
     */
    private void validateDate(GameUpdateDTO gameUpdateDTO) throws AlexthequeStandardError{
        if(gameUpdateDTO.getStartDate() != null) validateStartDate(gameUpdateDTO);
        if(gameUpdateDTO.getEndDate() != null){
                if(gameUpdateDTO.getStartDate() == null){
                    throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "You must have a start date before entering a end date.");
                }
                else{
                    validateEndDate(gameUpdateDTO);
                }
        }
    }

    /**
     * Validate the date
     * @param gameUpdateDTO - the gameUpdateDTO to validate
     * @throws AlexthequeStandardError if there is an error in a date
     */
    private void validateEndDate(GameUpdateDTO gameUpdateDTO) throws AlexthequeStandardError {
        if(gameUpdateDTO.getEndDate().isAfter(LocalDate.now())){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "The end date should not be in the future.");
        }
    }

    /**
     * Validate the date
     * @param gameUpdateDTO - the gameUpdateDTO to validate
     * @throws AlexthequeStandardError if there is an error in a date
     */
    private void validateStartDate(GameUpdateDTO gameUpdateDTO) throws AlexthequeStandardError {
        if(gameUpdateDTO.getStartDate().isAfter(LocalDate.now())){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "StartDate should not be in the future.");
        }
    }
}
