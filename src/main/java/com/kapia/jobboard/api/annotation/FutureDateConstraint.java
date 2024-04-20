package com.kapia.jobboard.api.annotation;

import com.kapia.jobboard.api.validation.FutureDateConstraintValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = FutureDateConstraintValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface FutureDateConstraint {

    String message() default "Date must be at least 7 days in the future";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

}
