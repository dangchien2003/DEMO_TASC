package org.example.restcontroller_controller.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEqualsValidator implements ConstraintValidator<NotEqualsConstraint, String> {
    private String value;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.equals(this.value);
    }

    @Override
    public void initialize(NotEqualsConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        value = constraintAnnotation.value();
    }
}
