package com.kapia.jobboard.api.validation;

import com.kapia.jobboard.api.annotation.SalaryTypeSubset;
import com.kapia.jobboard.api.constants.SalaryType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class SalaryTypeSubsetValidator implements ConstraintValidator<SalaryTypeSubset, SalaryType> {

    private SalaryType[] subset;

    @Override
    public void initialize(SalaryTypeSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(SalaryType salaryType, ConstraintValidatorContext constraintValidatorContext) {
        return salaryType == null || Arrays.asList(subset).contains(salaryType);
    }

}
