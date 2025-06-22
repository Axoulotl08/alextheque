package com.axoulotl.alextheque.service.validation;

import com.axoulotl.alextheque.exception.AlexthequeStandardError;
import com.axoulotl.alextheque.exception.StandardErrorEnum;
import com.axoulotl.alextheque.model.dto.input.GameDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class GameValidationService {
    public void validateGameInsert(GameDTO gameDTO) throws AlexthequeStandardError {
        validateGameName(gameDTO);
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

}
