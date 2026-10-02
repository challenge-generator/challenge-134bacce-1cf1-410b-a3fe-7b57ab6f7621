package com.ecommerce.comments.infrastructure.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record CreateCommentRequest(
    @NotNull(message = "El ID del producto es obligatorio")
    UUID productId,
    @NotNull(message = "El ID del usuario es obligatorio")
    UUID userId,
    @NotBlank(message = "El contenido del comentario no puede estar vacío")
    @Size(min = 10, max = 2000, message = "El contenido debe tener entre 10 y 2000 caracteres")
    String content,
    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    Integer rating,
    String idempotencyKey
) {
    public CreateCommentRequest {
        if (content != null) {
            content = content.trim();
        }
    }

    public boolean hasIdempotencyKey() {
        return idempotencyKey != null && !idempotencyKey.isBlank();
    }
}