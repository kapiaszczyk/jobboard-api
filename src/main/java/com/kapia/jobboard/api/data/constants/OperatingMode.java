package com.kapia.jobboard.api.data.constants;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum OperatingMode {
    REMOTE("remote"),
    HYBRID("hybrid"),
    ONSITE("onsite");

    private final String value;

    private static final Map<String, OperatingMode> OPERATING_MODE_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(OperatingMode::getValue, Function.identity()));

    OperatingMode(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static OperatingMode fromString(final String value) {
        return OPERATING_MODE_MAP.get(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
