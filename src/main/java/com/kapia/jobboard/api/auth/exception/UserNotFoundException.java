package com.kapia.jobboard.api.auth.exception;

/**
 * Exception thrown when a user is not found.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserNotFoundException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new UserNotFoundException with the specified cause.
     *
     * @param cause the cause
     */
    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new UserNotFoundException.
     */
    public UserNotFoundException() {
        super();
    }
}
