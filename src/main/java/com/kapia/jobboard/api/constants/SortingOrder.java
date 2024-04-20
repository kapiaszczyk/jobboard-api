package com.kapia.jobboard.api.constants;

public enum SortingOrder {

    ASC,
    DESC;

    public static SortingOrder fromString(String value) {
        switch (value) {
            case "ASC":
                return ASC;
            case "DESC":
                return DESC;
            default:
                throw new IllegalArgumentException("Unknown constants order: " + value);
        }
    }

}
