package com.ecommerce.comments.infrastructure.persistence;

import com.ecommerce.comments.domain.model.Comment;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments", indexes = {
    @Index(name = "idx_product_id", columnList = "product_id"),
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_product_user", columnList = "product_id, user_id", unique = true)
})
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "is_visible", nullable = false)
    private Boolean isVisible;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "idempotency_key", length = 255)
    private String idempotencyKey;

    public CommentEntity() {
    }

    public CommentEntity(UUID productId, UUID userId, String content, Integer rating, Boolean isVisible) {
        this.productId = productId;
        this.userId = userId;
        this.content = content;
        this.rating = rating;
        this.isVisible = isVisible;
    }

    public static CommentEntity fromDomain(Comment comment) {
        CommentEntity entity = new CommentEntity();
        entity.id = comment.id();
        entity.productId = comment.productId();
        entity.userId = comment.userId();
        entity.content = comment.content();
        entity.rating = comment.rating();
        entity.isVisible = comment.isVisible();
        entity.createdAt = comment.createdAt();
        return entity;
    }

    public Comment toDomain() {
        return Comment.fromPersistence(
            this.id,
            this.productId,
            this.userId,
            this.content,
            this.rating,
            this.isVisible,
            this.createdAt
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }
}