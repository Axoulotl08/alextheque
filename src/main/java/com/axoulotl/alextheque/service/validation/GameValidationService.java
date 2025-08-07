package com.axoulotl.alextheque.service.validation;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import com.axoulotl.alextheque.model.dto.input.GameUpdateDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class GameValidationService {
    /**
     * Validate all the DTO data through many method
     *
     * @param gameDTO - The gameDTO in entry
     * @throws AlexthequeStandardError if there is any error during the validation of the DTO.
     */
    public void validateGameInsert(GameDTO gameDTO) throws AlexthequeStandardError {
        validateDTO(gameDTO);
        validateGameName(gameDTO);
        validateGameConsole(gameDTO);
    }

    private static void validateDTO(GameDTO gameDTO) throws AlexthequeStandardError {
        if(Objects.isNull(gameDTO)){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "The GameDTO should not be null.");
        }
    }

    /**
     * Validate the gameId console
     * Should be greater than 0.
     * Check is console existe is made in service
     * @param gameDTO - GameDTO to validate the console
     */
    private void validateGameConsole(GameDTO gameDTO) throws AlexthequeStandardError {
        if(gameDTO.getConsole() <= 0){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "Console Id should be greater than 0.");
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
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "The name should not be empty or null.");
        }
    }

    /**
     * Validate a update DTO for a game
     *
     * @param gameUpdateDTO - the gameUpdateDTO to validate
     * @throws AlexthequeStandardError if any field is not validate
     */
    public void validateGameUpdate(GameUpdateDTO gameUpdateDTO) throws AlexthequeStandardError {
        if(Objects.isNull(gameUpdateDTO)){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "The Update DTO should not be null.");
        }
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
        if(gameUpdateDTO.getEndDate().isBefore(gameUpdateDTO.getStartDate())){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "The end date should be after the start date.");
        }
        if(gameUpdateDTO.getEndDate().isAfter(LocalDate.now())){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "The end date should be before or today.");
        }
    }

    /**
     * Validate the date
     * @param gameUpdateDTO - the gameUpdateDTO to validate
     * @throws AlexthequeStandardError if there is an error in a date
     */
    private void validateStartDate(GameUpdateDTO gameUpdateDTO) throws AlexthequeStandardError {
        if(gameUpdateDTO.getStartDate().isAfter(LocalDate.now())){
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "The start date should be before or today.");
        }
    }
}
