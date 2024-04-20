package com.kapia.jobboard.api.validation;

import com.kapia.jobboard.api.annotation.DegreeOfKnowledgeSubset;
import com.kapia.jobboard.api.constants.DegreeOfKnowledge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class DegreeOfKnowledgeSubsetValidator implements ConstraintValidator<DegreeOfKnowledgeSubset, DegreeOfKnowledge> {

    private DegreeOfKnowledge[] subset;

    @Override
    public void initialize(DegreeOfKnowledgeSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(DegreeOfKnowledge degree, ConstraintValidatorContext context) {
        return degree == null || Arrays.asList(subset).contains(degree);
    }

}
