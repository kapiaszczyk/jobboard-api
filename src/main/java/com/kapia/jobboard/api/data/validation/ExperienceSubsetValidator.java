package com.kapia.jobboard.api.data.validation;

import com.kapia.jobboard.api.data.annotation.ExperienceSubset;
import com.kapia.jobboard.api.data.constants.Experience;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ExperienceSubsetValidator implements ConstraintValidator<ExperienceSubset, Experience> {

    private Experience[] subset;

    @Override
    public void initialize(ExperienceSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(Experience experience, ConstraintValidatorContext constraintValidatorContext) {
        return experience == null || Arrays.asList(subset).contains(experience);
    }


}
