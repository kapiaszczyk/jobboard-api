package com.kapia.jobboard.api.data.constants;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The contract type enumeration.
 */
public enum ContractType {

    FULL_TIME("full-time"),
    PART_TIME("part-time"),
    TEMPORARY("temporary"),
    CONTRACT("contract"),
    OTHER("other"),
    INTERNSHIP("internship"),
    B2B("b2b");

    private final String value;

    private static final Map<String, ContractType> CONTRACT_TYPE_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(ContractType::getValue, Function.identity()));

    ContractType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ContractType fromString(final String value) {
        return CONTRACT_TYPE_MAP.get(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
