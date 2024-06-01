package com.kapia.jobboard.api.auth.exception;

/**
 * Exception thrown when a role is not found.
 */
public class RoleNotFoundException extends RuntimeException {

    /**
     * Constructs a new RoleNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public RoleNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new RoleNotFoundException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public RoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new RoleNotFoundException with the specified cause.
     *
     * @param cause the cause
     */
    public RoleNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new RoleNotFoundException.
     */
    public RoleNotFoundException() {
        super();
    }

}
