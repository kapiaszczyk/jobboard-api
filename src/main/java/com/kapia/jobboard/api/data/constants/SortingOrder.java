package com.kapia.jobboard.api.data.constants;

/**
 * The SortingOrder class is a constant class that holds the valid sorting orders.
 */
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
