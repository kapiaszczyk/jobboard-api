package com.kapia.jobboard.api.data.annotation;

import com.kapia.jobboard.api.data.constants.Experience;
import com.kapia.jobboard.api.data.validation.ExperienceSubsetValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The ExperienceSubset interface is a constraint annotation that validates that the experience type is any of the specified experience types.
 */
@Constraint(validatedBy = ExperienceSubsetValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface ExperienceSubset {

    Experience[] anyOf();

    String message() default "Experience type must be any of {anyOf}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
