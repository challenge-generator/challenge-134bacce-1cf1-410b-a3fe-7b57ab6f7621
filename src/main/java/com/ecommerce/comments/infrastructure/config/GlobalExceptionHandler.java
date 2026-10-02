package com.ecommerce.comments.infrastructure.config;

import com.ecommerce.comments.domain.exceptions.CommentValidationException;
import com.ecommerce.comments.domain.exceptions.DuplicateCommentException;
import com.ecommerce.comments.domain.exceptions.StorageException;
import com.ecommerce.comments.infrastructure.api.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String DEFAULT_ERROR_MESSAGE = "Ha ocurrido un error interno en el sistema";

    @ExceptionHandler(CommentValidationException.class)
    public ResponseEntity<ErrorResponse> handleCommentValidationException(
            CommentValidationException ex, 
            WebRequest request) {
        
        logger.warn("Error de validación de comentario: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Validation Error")
            .message(ex.getMessage())
            .path(getPathFromRequest(request))
            .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DuplicateCommentException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCommentException(
            DuplicateCommentException ex, 
            WebRequest request) {
        
        logger.warn("Intento de comentario duplicado: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.CONFLICT.value())
            .error("Conflict")
            .message(ex.getMessage())
            .path(getPathFromRequest(request))
            .build();
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ErrorResponse> handleStorageException(
            StorageException ex, 
            WebRequest request) {
        
        logger.error("Error de almacenamiento: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error("Storage Error")
            .message("Error al procesar el comentario en el almacenamiento")
            .path(getPathFromRequest(request))
            .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, 
            WebRequest request) {
        
        Map<String, String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                error -> error.getDefaultMessage() != null 
                    ? error.getDefaultMessage() 
                    : "Valor inválido",
                (existing, replacement) -> existing
            ));
        
        logger.warn("Errores de validación en request: {}", errors);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Validation Failed")
            .message("Los datos proporcionados no son válidos")
            .path(getPathFromRequest(request))
            .validationErrors(errors)
            .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, 
            WebRequest request) {
        
        logger.warn("Argumento ilegal: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Bad Request")
            .message(ex.getMessage())
            .path(getPathFromRequest(request))
            .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, 
            WebRequest request) {
        
        logger.error("Error no manejado: {}", ex.getMessage(), ex);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
            .timestamp(Instant.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error("Internal Server Error")
            .message(DEFAULT_ERROR_MESSAGE)
            .path(getPathFromRequest(request))
            .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private String getPathFromRequest(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}