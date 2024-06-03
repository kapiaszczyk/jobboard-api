package com.kapia.jobboard.api.data.annotation;

import com.kapia.jobboard.api.data.constants.OperatingMode;
import com.kapia.jobboard.api.data.validation.OperatingModeSubsetValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The OperatingModeSubset interface is a constraint annotation that validates that the operating mode is any of the specified operating modes.
 */
@Constraint(validatedBy = OperatingModeSubsetValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface OperatingModeSubset {

    OperatingMode[] anyOf();

    String message() default "Operating mode must be any of {anyOf}";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

}
