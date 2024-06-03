package com.kapia.jobboard.api.data.validation;

import com.kapia.jobboard.api.data.annotation.SalaryTypeSubset;
import com.kapia.jobboard.api.data.constants.SalaryType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

/**
 * The SalaryTypeSubsetValidator class is a validator class that validates the SalaryType enum.
 */
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
