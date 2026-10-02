package com.ecommerce.comments.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    int status,
    String error,
    String message,
    String path,
    Instant timestamp,
    List<FieldError> details
) {
    public ErrorResponse(int status, String error, String message, String path) {
        this(status, error, message, path, Instant.now(), new ArrayList<>());
    }

    public ErrorResponse(int status, String error, String message, String path, List<FieldError> details) {
        this(status, error, message, path, Instant.now(), details);
    }

    public static ErrorResponse badRequest(String message, String path) {
        return new ErrorResponse(400, "Bad Request", message, path);
    }

    public static ErrorResponse notFound(String message, String path) {
        return new ErrorResponse(404, "Not Found", message, path);
    }

    public static ErrorResponse conflict(String message, String path) {
        return new ErrorResponse(409, "Conflict", message, path);
    }

    public static ErrorResponse internalError(String message, String path) {
        return new ErrorResponse(500, "Internal Server Error", message, path);
    }

    public ErrorResponse withDetails(List<FieldError> newDetails) {
        return new ErrorResponse(this.status(), this.error(), this.message(), this.path(), this.timestamp(), newDetails);
    }

    public record FieldError(
        String field,
        String message,
        Object rejectedValue
    ) {
        public FieldError(String field, String message) {
            this(field, message, null);
        }
    }
}