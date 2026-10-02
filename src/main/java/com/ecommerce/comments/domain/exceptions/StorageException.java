package com.ecommerce.comments.domain.exceptions;


import com.ecommerce.comments.domain.model.Comment;
import java.util.UUID;

public class StorageException extends RuntimeException {

    private final UUID commentId;
    private final String operation;
    private final String storageContext;
    private final boolean retryable;

    public StorageException(String message) {
        super(message);
        this.commentId = null;
        this.operation = null;
        this.storageContext = null;
        this.retryable = false;
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
        this.commentId = null;
        this.operation = null;
        this.storageContext = null;
        this.retryable = determineRetryable(cause);
    }

    public StorageException(String message, UUID commentId, String operation) {
        super(message);
        this.commentId = commentId;
        this.operation = operation;
        this.storageContext = null;
        this.retryable = false;
    }

    public StorageException(String message, UUID commentId, String operation, Throwable cause) {
        super(message, cause);
        this.commentId = commentId;
        this.operation = operation;
        this.storageContext = null;
        this.retryable = determineRetryable(cause);
    }

    public StorageException(String message, UUID commentId, String operation, String storageContext) {
        super(message);
        this.commentId = commentId;
        this.operation = operation;
        this.storageContext = storageContext;
        this.retryable = false;
    }

    public StorageException(String message, UUID commentId, String operation, String storageContext, 
                           Throwable cause) {
        super(message, cause);
        this.commentId = commentId;
        this.operation = operation;
        this.storageContext = storageContext;
        this.retryable = determineRetryable(cause);
    }

    public StorageException(String message, UUID commentId, String operation, String storageContext, 
                           boolean retryable, Throwable cause) {
        super(message, cause);
        this.commentId = commentId;
        this.operation = operation;
        this.storageContext = storageContext;
        this.retryable = retryable;
    }

    private static boolean determineRetryable(Throwable cause) {
        if (cause == null) {
            return false;
        }
        String exceptionClassName = cause.getClass().getSimpleName().toLowerCase();
        return exceptionClassName.contains("timeout") || 
               exceptionClassName.contains("connection") ||
               exceptionClassName.contains("transient");
    }

    public UUID getCommentId() {
        return commentId;
    }

    public String getOperation() {
        return operation;
    }

    public String getStorageContext() {
        return storageContext;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public boolean hasCommentId() {
        return commentId != null;
    }

    public boolean hasOperation() {
        return operation != null && !operation.isEmpty();
    }

    public boolean hasStorageContext() {
        return storageContext != null && !storageContext.isEmpty();
    }

    public static StorageException forSaveFailure(UUID commentId, Throwable cause) {
        return new StorageException(
            "Error al guardar el comentario en el almacenamiento",
            commentId,
            "SAVE",
            cause
        );
    }

    public static StorageException forDeleteFailure(UUID commentId, Throwable cause) {
        return new StorageException(
            "Error al eliminar el comentario del almacenamiento",
            commentId,
            "DELETE",
            cause
        );
    }

    public static StorageException forRetrieveFailure(UUID commentId, Throwable cause) {
        return new StorageException(
            "Error al recuperar el comentario del almacenamiento",
            commentId,
            "RETRIEVE",
            cause
        );
    }

    public static StorageException forConnectionFailure(String storageContext, Throwable cause) {
        return new StorageException(
            "Error de conexión con el almacenamiento",
            null,
            "CONNECT",
            storageContext,
            true,
            cause
        );
    }

    public static StorageException forDatabaseError(String operation, Throwable cause) {
        return new StorageException(
            "Error de base de datos durante la operación: " + operation,
            null,
            operation,
            "DATABASE",
            cause
        );
    }

    public String getDetailedMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("StorageException: ").append(getMessage());
        
        if (hasCommentId()) {
            sb.append(" | Comment ID: ").append(commentId);
        }
        if (hasOperation()) {
            sb.append(" | Operation: ").append(operation);
        }
        if (hasStorageContext()) {
            sb.append(" | Context: ").append(storageContext);
        }
        sb.append(" | Retryable: ").append(retryable);
        
        return sb.toString();
    }
}