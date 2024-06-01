package com.kapia.jobboard.api.auth.exceptionhandler;

import com.kapia.jobboard.api.auth.exception.RoleNotFoundException;
import com.kapia.jobboard.api.auth.exception.UserAlreadyExistsException;
import com.kapia.jobboard.api.auth.exception.UserNotFoundException;
import com.kapia.jobboard.api.data.constants.Messages;
import com.kapia.jobboard.api.data.error.ErrorResponse;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global exception handler for the authentication controller.
 * This class handles various exceptions that can occur during authentication operations and returns appropriate error responses.
 */
@ControllerAdvice(assignableTypes = AuthControllerExceptionHandler.class)
public class AuthControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthControllerExceptionHandler.class);

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    private ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException e) {

        ErrorResponse errorResponse = new ErrorResponse.Builder()
                .status(HttpStatus.CONFLICT)
                .message(e.getMessage())
                .error(Messages.USER_ALREADY_EXISTS_ERR).build();

        LOGGER.info(errorResponse.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse.toJSON());

    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<?> handleUserNotFoundExceptionException(UserNotFoundException e) {

        ErrorResponse errorResponse = new ErrorResponse.Builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .error(Messages.USER_NOT_FOUND_ERR).build();

        LOGGER.info(errorResponse.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse.toJSON());

    }

    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<?> handleRoleNotFoundExceptionException(RoleNotFoundException e) {

        ErrorResponse errorResponse = new ErrorResponse.Builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .error(Messages.ROLE_NOT_FOUND_ERR).build();

        LOGGER.info(errorResponse.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse.toJSON());

    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    private ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {

        ErrorResponse errorResponse = new ErrorResponse.Builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(e.getMessage())
                .error(Messages.RESOURCE_NOT_FOUND_ERR).build();

        LOGGER.info(errorResponse.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse.toJSON());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {

        ErrorResponse errorResponse = new ErrorResponse.Builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .error(Messages.USER_NOT_FOUND_ERR).build();

        LOGGER.info(errorResponse.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse.toJSON());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<?> handleValidationException(ValidationException e) {

        ErrorResponse errorResponse = new ErrorResponse.Builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .error(Messages.INVALID_DATA_ERR).build();

        LOGGER.info(errorResponse.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse.toJSON());
    }
}
