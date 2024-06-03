package com.kapia.jobboard.api.data.annotation;

import com.kapia.jobboard.api.data.validation.FutureDateConstraintValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The FutureDateConstraint interface is a constraint annotation that validates that the date is at least 7 days in the future.
 */
@Constraint(validatedBy = FutureDateConstraintValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface FutureDateConstraint {

    String message() default "Date must be at least 7 days in the future";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

}
