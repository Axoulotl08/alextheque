package com.axoulotl.alextheque.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorException(
        LocalDateTime timestamp,
        int status,
        String message,
        Map<String, String> error
) {
}
