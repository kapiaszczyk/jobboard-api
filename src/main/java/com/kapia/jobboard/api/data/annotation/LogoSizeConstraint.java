package com.kapia.jobboard.api.data.annotation;

import com.kapia.jobboard.api.data.validation.LogoSizeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The LogoSizeConstraint interface is a constraint annotation that validates that the logo size is less than or equal to 1MB.
 */
@Constraint(validatedBy = LogoSizeValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RUNTIME)
public @interface LogoSizeConstraint {

    String message() default "Invalid logo size";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
