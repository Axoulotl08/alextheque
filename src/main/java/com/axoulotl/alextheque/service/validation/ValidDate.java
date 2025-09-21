package com.axoulotl.alextheque.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidDateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {
    String message() default "Invalid Date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
