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

    private void validateGameName(GameDTO gameDTO) throws AlexthequeStandardError {
        if(StringUtils.isEmpty(gameDTO.getName())) {
            throw new AlexthequeStandardError(StandardErrorEnum.ERROR_INPUT, "Le nom ne doit pas être nul");
        }
    }
}
