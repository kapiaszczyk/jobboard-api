package com.kapia.jobboard.api.auth.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The RoleEnum class represents the different roles that a user can have in the system.
 */
public enum RoleEnum {

    /**
     * The USER role.
     */
    USER("USER"),

    /**
     * The ADMIN role.
     */
    ADMIN("ADMIN"),

    /**
     * The SUPER_ADMIN role.
     */
    SUPER_ADMIN("SUPER_ADMIN");

    private final String value;

    private static final Map<String, RoleEnum> ROLE_ENUM_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(RoleEnum::getValue, Function.identity()));

    /**
     * Constructs a new RoleEnum with the specified value.
     *
     * @param value the value of the role
     */
    RoleEnum(final String value) {
        this.value = value;
    }

    /**
     * Returns the RoleEnum corresponding to the specified value.
     *
     * @param value the value of the role
     * @return the RoleEnum corresponding to the specified value, or null if not found
     */
    public static RoleEnum fromString(final String value) {
        return ROLE_ENUM_MAP.get(value);
    }

    /**
     * Returns the value of the role.
     *
     * @return the value of the role
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns the string representation of the role.
     *
     * @return the string representation of the role
     */
    @Override
    public String toString() {
        return value;
    }

}
