package com.kapia.jobboard.api.data.constants;

/**
 * The Currencies class is a constant class that holds the valid currencies.
 */
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum DegreeOfKnowledge {

    NONE("no knowledge required"),
    BEGINNER("beginner"),
    INTERMEDIATE("intermediate"),
    ADVANCED("advanced"),
    EXPERT("expert");

    private final String value;

    private static final Map<String, DegreeOfKnowledge> DEGREE_OF_KNOWLEDGE_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(DegreeOfKnowledge::getValue, Function.identity()));

    DegreeOfKnowledge(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DegreeOfKnowledge fromString(String value) {
        return DEGREE_OF_KNOWLEDGE_MAP.get(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
