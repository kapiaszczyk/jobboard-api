package com.kapia.jobboard.api.annotation;

import com.kapia.jobboard.api.validation.LogoSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = LogoSizeValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RUNTIME)
public @interface LogoSizeConstraint {

    String message() default "Invalid logo size";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
