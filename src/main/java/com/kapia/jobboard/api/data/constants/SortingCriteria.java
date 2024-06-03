package com.kapia.jobboard.api.data.constants;

/**
 * The SortingCriteria class is a constant class that holds the valid sorting criteria.
 */
public enum SortingCriteria {

    NAME {
        @Override
        public String toString() {
            return "name";
        }
    },
    CREATED_AT {
        @Override
        public String toString() {
            return "createdAt";
        }
    },
    COMPANY_NAME {
        @Override
        public String toString() {
            return "company.name";
        }
    },
    EXPIRES_AT {
        @Override
        public String toString() {
            return "expiresAt";
        }
    },
    SALARY {
        @Override
        public String toString() {
            return "salary";
        }
    };

    public static SortingCriteria fromString(String value) {
        switch (value) {
            case "NAME":
                return NAME;
            case "CREATED_AT":
                return CREATED_AT;
            case "COMPANY_NAME":
                return COMPANY_NAME;
            case "EXPIRES_AT":
                return EXPIRES_AT;
            case "SALARY":
                return SALARY;
            default:
                throw new IllegalArgumentException("Unknown constants criteria: " + value);
        }
    }

}
