package com.kapia.jobboard.api.sorting;

public enum SortingCriteria {

    name,
    createdAt,
    companyName,
    expiresAt,
    salary;

    public static SortingCriteria fromString(String value) {
        switch (value) {
            case "name":
                return name;
            case "createdAt":
                return createdAt;
            case "COMPANY_NAME":
                return companyName;
            case "expiresAt":
                return expiresAt;
            case "salary":
                return salary;
            default:
                throw new IllegalArgumentException("Unknown sorting criteria: " + value);
        }
    }

}
