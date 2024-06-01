package com.kapia.jobboard.api.auth.exception;

/**
 * Exception thrown when a user already exists.
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new UserAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserAlreadyExistsException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new UserAlreadyExistsException with the specified cause.
     *
     * @param cause the cause
     */
    public UserAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new UserAlreadyExistsException.
     */
    public UserAlreadyExistsException() {
        super();
    }
}
