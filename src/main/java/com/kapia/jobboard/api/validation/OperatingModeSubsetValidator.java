package com.kapia.jobboard.api.validation;

import com.kapia.jobboard.api.annotation.OperatingModeSubset;
import com.kapia.jobboard.api.constants.OperatingMode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class OperatingModeSubsetValidator implements ConstraintValidator<OperatingModeSubset, OperatingMode> {

    private OperatingMode[] subset;

    @Override
    public void initialize(OperatingModeSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(OperatingMode operatingMode, ConstraintValidatorContext constraintValidatorContext) {
        return operatingMode == null || Arrays.asList(subset).contains(operatingMode);
    }


}
