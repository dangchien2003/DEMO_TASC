package org.example.restcontroller_controller.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotEqualsValidator.class})
public @interface NotEqualsConstraint {

    String message() default "Dữ liệu không trùng khớp";

    String value();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
