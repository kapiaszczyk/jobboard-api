package com.kapia.jobboard.api.data.annotation;

import com.kapia.jobboard.api.data.constants.DegreeOfKnowledge;
import com.kapia.jobboard.api.data.validation.DegreeOfKnowledgeSubsetValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The DegreeOfKnowledgeSubset interface is a constraint annotation that validates that the degree of knowledge is any of the specified degrees of knowledge.
 */
@Constraint(validatedBy = DegreeOfKnowledgeSubsetValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface DegreeOfKnowledgeSubset {

    DegreeOfKnowledge[] anyOf();

    String message() default "Degree of knowledge must be any of {anyOf}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
