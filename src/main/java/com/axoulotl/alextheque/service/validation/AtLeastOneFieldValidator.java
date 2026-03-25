package com.axoulotl.alextheque.service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class AtLeastOneFieldValidator implements ConstraintValidator<AtLeastOneField, Object> {
    private String[] fields;

    @Override
    public void initialize(AtLeastOneField constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        if (object == null){
            return true;
        }

        BeanWrapper beanWrapper = new BeanWrapperImpl(object);

        for (String field : this.fields) {
            Object value = beanWrapper.getPropertyValue(field);
            if (value instanceof String stringValue){
                if(StringUtils.isNotBlank(stringValue)){
                    return true;
                }
            }
            else{
                return true;
            }
        }
        return false;
    }
}
