package com.kapia.jobboard.api.data.error;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The ErrorResponse class is a data class that represents an error response.
 */
public class ErrorResponse {

    private Instant timestamp;
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ErrorResponse() {
    }

    public ErrorResponse(Instant timestamp, HttpStatus status, String message, List<String> errors) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String toJSON() {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule().addSerializer(Instant.class, new MyInstantSerializer()))
                .build();
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            return "";
        }
    }

    public static class Builder {

        private Instant timestamp;
        private HttpStatus status;
        private String message;
        private List<String> errors;

        public Builder() {
            this.timestamp = Instant.now();
        }

        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder errors(List<String> errors) {
            this.errors = errors;
            return this;
        }

        public Builder error(String error) {
            this.errors = List.of(error);
            return this;
        }

        public Builder errors(Map<String, String> errors) {
            this.errors = errors.entrySet().stream()
                    .map(entry -> entry.getKey() + ": " + entry.getValue())
                    .collect(Collectors.toList());
            return this;
        }

        public Builder error() {
            this.errors = List.of();
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(timestamp, status, message, errors);
        }

    }

    private static class MyInstantSerializer extends InstantSerializer {
        public MyInstantSerializer() {
            super(InstantSerializer.INSTANCE, false, false,
                    new DateTimeFormatterBuilder().appendInstant(3).toFormatter());
        }

        @Override
        public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
            return this;
        }
    }

}
