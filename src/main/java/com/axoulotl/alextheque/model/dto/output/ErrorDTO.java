package com.axoulotl.alextheque.model.dto.output;

import com.axoulotl.alextheque.exception.StandardErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDTO{
    private String message;
    private StandardErrorEnum typeError;
}
