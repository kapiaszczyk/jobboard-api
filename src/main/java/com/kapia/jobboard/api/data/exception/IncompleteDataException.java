package com.kapia.jobboard.api.data.exception;

/**
 * The IncompleteDataException class is a custom exception that is thrown when a required field is missing from a request.
 */
public class IncompleteDataException extends RuntimeException {

    public IncompleteDataException(String message) {
        super(message);
    }

    public IncompleteDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncompleteDataException(Throwable cause) {
        super(cause);
    }

    public IncompleteDataException() {
        super();
    }

}
