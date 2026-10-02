package com.ecommerce.comments.domain.ports;

import com.ecommerce.comments.domain.model.Comment;
import java.util.UUID;

public interface CommentValidator {
    void validateCommentContent(Comment comment);
    void validateCommentUniqueness(UUID productId, UUID userId);
    void validateCommentRating(Integer rating);

    class ValidationResult {
        private final boolean isValid;
        private final String errorMessage;

        public ValidationResult(boolean isValid, String errorMessage) {
            this.isValid = isValid;
            this.errorMessage = errorMessage;
        }

        public boolean isValid() {
            return isValid;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    default ValidationResult validateComment(Comment comment) {
        try {
            validateCommentContent(comment);
            validateCommentRating(comment.rating());
            validateCommentUniqueness(comment.productId(), comment.userId());
            return new ValidationResult(true, null);
        } catch (Exception e) {
            return new ValidationResult(false, e.getMessage());
        }
    }
}