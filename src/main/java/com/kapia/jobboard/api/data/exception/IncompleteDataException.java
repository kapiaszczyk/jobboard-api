package com.kapia.jobboard.api.data.exception;

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
