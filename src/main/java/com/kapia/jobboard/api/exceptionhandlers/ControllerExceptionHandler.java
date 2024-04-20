package com.kapia.jobboard.api.exceptionhandlers;

import com.kapia.jobboard.api.constants.Messages;
import com.kapia.jobboard.api.error.ErrorResponse;
import com.kapia.jobboard.api.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {

        String message = "Invalid input data";
        Map<String, String> errors = parseMessage(e);
        ErrorResponse errorResponse = new ErrorResponse.Builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(message)
                .errors(errors).build();

        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse.toJSON());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse.Builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .error(Messages.RESOURCE_NOT_FOUND_ERR).build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse.toJSON());
    }

    public Map<String, String> parseMessage(ConstraintViolationException e) {
        return e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        violation -> violation.getMessage()
                ));
    }

}
