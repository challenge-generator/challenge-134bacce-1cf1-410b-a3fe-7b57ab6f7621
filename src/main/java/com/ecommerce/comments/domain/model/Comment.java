package com.ecommerce.comments.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

public record Comment(
    @NotNull
    UUID commentId,

    @NotNull
    UUID productId,

    @NotNull
    UUID userId,

    @NotBlank
    @Size(min = 10, max = 1000)
    String content,

    @NotNull
    LocalDateTime createdAt,

    @NotNull
    Boolean isVisible,

    @NotNull
    Integer rating
) {
    public Comment {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        if (content.length() < 10 || content.length() > 1000) {
            throw new IllegalArgumentException("Content must be between 10 and 1000 characters");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (isVisible == null) {
            isVisible = true;
        }
    }

    public static Comment create(
            UUID commentId,
            UUID productId,
            UUID userId,
            String content,
            Integer rating
    ) {
        return new Comment(
                commentId != null ? commentId : UUID.randomUUID(),
                productId,
                userId,
                content,
                LocalDateTime.now(),
                true,
                rating
        );
    }

    public boolean isContentAppropriate() {
        String[] inappropriateWords = {"inapropiado", "ofensivo", "malo", "horrible"};
        String lowerContent = content.toLowerCase();
        for (String word : inappropriateWords) {
            if (lowerContent.contains(word)) {
                return false;
            }
        }
        return true;
    }

    public Comment withVisibility(boolean isVisible) {
        return new Comment(
                this.commentId,
                this.productId,
                this.userId,
                this.content,
                this.createdAt,
                isVisible,
                this.rating
        );
    }
}