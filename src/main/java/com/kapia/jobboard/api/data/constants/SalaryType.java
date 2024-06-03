package com.kapia.jobboard.api.data.constants;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The SalaryType class is a constant class that holds the valid salary types.
 */
public enum SalaryType {
    HOURLY("hourly"),
    MONTHLY("monthly"),
    ANNUAL("annual"),
    OTHER("other");

    private final String value;

    private static final Map<String, SalaryType> SALARY_TYPE_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(SalaryType::getValue, Function.identity()));

    SalaryType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SalaryType fromString(final String value) {
        return SALARY_TYPE_MAP.get(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
