package com.kapia.jobboard.api.data.constants;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Experience {

    INTERN("intern"),
    JUNIOR("junior"),
    REGULAR("regular"),
    MID("mid"),
    SENIOR("senior"),
    EXPERT("expert"),
    ARCHITECT("architect"),
    OTHER("other");

    private final String value;

    private static final Map<String, Experience> EXPERIENCE_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(Experience::getValue, Function.identity()));

    Experience(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Experience fromString(final String value) {
        return EXPERIENCE_MAP.get(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
