package com.axoulotl.alextheque.service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {
    private String startFieldName;
    private String endFieldName;

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        this.startFieldName = constraintAnnotation.start();
        this.endFieldName = constraintAnnotation.end();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);

        Object startValue = beanWrapper.getPropertyValue(startFieldName);
        Object endValue = beanWrapper.getPropertyValue(endFieldName);

        if(startValue == null && endValue == null) {
            return true;
        }

        if(endValue == null){
            return true;
        }

        switch (startValue) {
            case null -> {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        "Start date must be filed if an end date is specified"
                ).addPropertyNode(startFieldName).addConstraintViolation();
                return false;
            }
            case LocalDate startDate when endValue instanceof LocalDate endDate -> {
                if (endDate.isBefore(startDate) || endDate.isEqual(startDate)) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(
                            "End date must be after start date"
                    ).addPropertyNode(endFieldName).addConstraintViolation();
                    return false;
                }
            }
            case LocalDateTime startDateTime when endValue instanceof LocalDateTime endDateTime -> {
                if (endDateTime.isBefore(startDateTime) || endDateTime.isEqual(startDateTime)) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(
                            "End date must be after start date"
                    ).addPropertyNode(endFieldName).addConstraintViolation();
                    return false;
                }
            }
            default -> {
            }
        }

        return true;
    }


}
