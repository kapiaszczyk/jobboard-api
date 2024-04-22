package com.kapia.jobboard.api.auth.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum RoleEnum {

    USER("USER"),
    ADMIN("ADMIN"),
    SUPER_ADMIN("SUPER_ADMIN");
    private final String value;

    private static final Map<String, RoleEnum> ROLE_ENUM_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(RoleEnum::getValue, Function.identity()));

    RoleEnum(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RoleEnum fromString(final String value) {
        return ROLE_ENUM_MAP.get(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
