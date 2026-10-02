package com.ecommerce.comments.domain.exceptions;


import com.ecommerce.comments.domain.model.Comment;
import java.time.Instant;
import java.util.UUID;

public class DuplicateCommentException extends RuntimeException {

    private final UUID productId;
    private final UUID userId;
    private final UUID existingCommentId;
    private final Instant existingCommentTimestamp;
    private final String idempotencyKey;

    public DuplicateCommentException(String message) {
        super(message);
        this.productId = null;
        this.userId = null;
        this.existingCommentId = null;
        this.existingCommentTimestamp = null;
        this.idempotencyKey = null;
    }

    public DuplicateCommentException(String message, Throwable cause) {
        super(message, cause);
        this.productId = null;
        this.userId = null;
        this.existingCommentId = null;
        this.existingCommentTimestamp = null;
        this.idempotencyKey = null;
    }

    public DuplicateCommentException(UUID productId, UUID userId, UUID existingCommentId) {
        super(String.format("El usuario %s ya ha comentado el producto %s", userId, productId));
        this.productId = productId;
        this.userId = userId;
        this.existingCommentId = existingCommentId;
        this.existingCommentTimestamp = null;
        this.idempotencyKey = null;
    }

    public DuplicateCommentException(UUID productId, UUID userId, UUID existingCommentId, 
                                     Instant existingCommentTimestamp) {
        super(String.format("El usuario %s ya ha comentado el producto %s el %s", 
            userId, productId, existingCommentTimestamp));
        this.productId = productId;
        this.userId = userId;
        this.existingCommentId = existingCommentId;
        this.existingCommentTimestamp = existingCommentTimestamp;
        this.idempotencyKey = null;
    }

    public DuplicateCommentException(UUID productId, UUID userId, String idempotencyKey) {
        super(String.format("Ya existe un comentario con la clave de idempotencia '%s' para el producto %s", 
            idempotencyKey, productId));
        this.productId = productId;
        this.userId = userId;
        this.existingCommentId = null;
        this.existingCommentTimestamp = null;
        this.idempotencyKey = idempotencyKey;
    }

    public UUID getProductId() {
        return productId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getExistingCommentId() {
        return existingCommentId;
    }

    public Instant getExistingCommentTimestamp() {
        return existingCommentTimestamp;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public boolean hasExistingComment() {
        return existingCommentId != null;
    }

    public boolean hasIdempotencyKey() {
        return idempotencyKey != null && !idempotencyKey.isEmpty();
    }

    public static DuplicateCommentException forExistingComment(UUID productId, UUID userId) {
        return new DuplicateCommentException(productId, userId, null);
    }

    public static DuplicateCommentException forIdempotencyKeyConflict(UUID productId, UUID userId, 
                                                                       String idempotencyKey) {
        return new DuplicateCommentException(productId, userId, idempotencyKey);
    }

    public static DuplicateCommentException forUserProductCombination(UUID productId, UUID userId, 
                                                                      UUID existingCommentId) {
        return new DuplicateCommentException(productId, userId, existingCommentId);
    }

    public String getDetailedMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("DuplicateCommentException: ").append(getMessage());
        
        if (productId != null) {
            sb.append(" | Product: ").append(productId);
        }
        if (userId != null) {
            sb.append(" | User: ").append(userId);
        }
        if (existingCommentId != null) {
            sb.append(" | Existing Comment: ").append(existingCommentId);
        }
        if (existingCommentTimestamp != null) {
            sb.append(" | Timestamp: ").append(existingCommentTimestamp);
        }
        if (idempotencyKey != null) {
            sb.append(" | Idempotency Key: ").append(idempotencyKey);
        }
        
        return sb.toString();
    }
}