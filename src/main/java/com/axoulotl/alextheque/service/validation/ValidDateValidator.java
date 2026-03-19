package com.axoulotl.alextheque.service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidDateValidator  implements ConstraintValidator<ValidDate, LocalDate> {
    @Override
    public void initialize(ValidDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return (localDate != null && !localDate.isAfter(LocalDate.now()));
    }
}
