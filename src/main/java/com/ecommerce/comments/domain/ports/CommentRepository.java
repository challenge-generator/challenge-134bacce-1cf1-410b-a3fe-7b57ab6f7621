package com.ecommerce.comments.domain.ports;

import com.ecommerce.comments.domain.model.Comment;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository {
    Comment save(Comment comment);
    Optional<Comment> findById(UUID commentId);
    Optional<Comment> findByProductIdAndUserId(UUID productId, UUID userId);
    void deleteById(UUID commentId);
}