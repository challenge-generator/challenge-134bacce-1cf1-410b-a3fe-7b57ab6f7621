package com.ecommerce.comments.domain.exceptions;

import java.util.List;
import java.util.UUID;

public class CommentValidationException extends RuntimeException {

    private final String fieldName;
    private final Object rejectedValue;
    private final List<String> validationErrors;

    public CommentValidationException(String message) {
        super(message);
        this.fieldName = null;
        this.rejectedValue = null;
        this.validationErrors = List.of(message);
    }

    public CommentValidationException(String message, Throwable cause) {
        super(message, cause);
        this.fieldName = null;
        this.rejectedValue = null;
        this.validationErrors = List.of(message);
    }

    public CommentValidationException(String message, String fieldName, Object rejectedValue) {
        super(message);
        this.fieldName = fieldName;
        this.rejectedValue = rejectedValue;
        this.validationErrors = List.of(message);
    }

    public CommentValidationException(List<String> validationErrors) {
        super(String.join("; ", validationErrors));
        this.fieldName = null;
        this.rejectedValue = null;
        this.validationErrors = validationErrors;
    }

    public CommentValidationException(String message, UUID productId, UUID userId) {
        super(message);
        this.fieldName = "comment";
        this.rejectedValue = String.format("productId=%s, userId=%s", productId, userId);
        this.validationErrors = List.of(message);
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }

    public boolean hasFieldErrors() {
        return fieldName != null && !fieldName.isEmpty();
    }

    public boolean hasMultipleErrors() {
        return validationErrors != null && validationErrors.size() > 1;
    }

    public static CommentValidationException forInvalidContent(String content) {
        return new CommentValidationException(
            "El contenido del comentario no es válido",
            "content",
            content != null ? content.substring(0, Math.min(50, content.length())) + "..." : null
        );
    }

    public static CommentValidationException forInvalidRating(Integer rating) {
        return new CommentValidationException(
            "La calificación debe estar entre 1 y 5",
            "rating",
            rating
        );
    }

    public static CommentValidationException forContentTooShort(int actualLength, int minLength) {
        return new CommentValidationException(
            String.format("El contenido es muy corto: %d caracteres mínimo requerido, %d proporcionados", minLength, actualLength),
            "content",
            actualLength
        );
    }

    public static CommentValidationException forContentTooLong(int actualLength, int maxLength) {
        return new CommentValidationException(
            String.format("El contenido excede el límite permitido: %d caracteres máximo, %d proporcionados", maxLength, actualLength),
            "content",
            actualLength
        );
    }

    public static CommentValidationException forInappropriateContent() {
        return new CommentValidationException(
            "El contenido contiene lenguaje inapropiado y no puede ser publicado",
            "content",
            "[CONTENT_REJECTED]"
        );
    }
}