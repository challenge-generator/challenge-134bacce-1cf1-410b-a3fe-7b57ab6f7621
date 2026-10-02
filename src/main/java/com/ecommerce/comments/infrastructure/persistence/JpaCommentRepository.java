package com.ecommerce.comments.infrastructure.persistence;

import com.ecommerce.comments.domain.model.Comment;
import com.ecommerce.comments.domain.ports.CommentRepository;
import com.ecommerce.comments.domain.exceptions.StorageException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Comment save(Comment comment) {
        try {
            CommentEntity entity = toEntity(comment);
            
            if (entity.getId() == null) {
                entity.setId(UUID.randomUUID());
                entity.setCreatedAt(java.time.Instant.now());
            }
            entity.setUpdatedAt(java.time.Instant.now());
            
            if (entityManager.contains(entity)) {
                entityManager.merge(entity);
            } else {
                entityManager.persist(entity);
            }
            
            entityManager.flush();
            entityManager.refresh(entity);
            
            return toDomain(entity);
        } catch (Exception e) {
            throw new StorageException("Error al guardar el comentario: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findById(UUID commentId) {
        try {
            CommentEntity entity = entityManager.find(CommentEntity.class, commentId);
            return Optional.ofNullable(entity).map(this::toDomain);
        } catch (Exception e) {
            throw new StorageException("Error al buscar el comentario por ID: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findByProductIdAndUserId(UUID productId, UUID userId) {
        try {
            Query query = entityManager.createQuery(
                "SELECT c FROM CommentEntity c WHERE c.productId = :productId AND c.userId = :userId",
                CommentEntity.class
            );
            query.setParameter("productId", productId);
            query.setParameter("userId", userId);
            query.setMaxResults(1);
            
            var result = query.getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(toDomain(result.get(0)));
        } catch (Exception e) {
            throw new StorageException("Error al buscar comentario por producto y usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(UUID commentId) {
        try {
            CommentEntity entity = entityManager.getReference(CommentEntity.class, commentId);
            entityManager.remove(entity);
            entityManager.flush();
        } catch (Exception e) {
            throw new StorageException("Error al eliminar el comentario: " + e.getMessage(), e);
        }
    }

    private CommentEntity toEntity(Comment comment) {
        CommentEntity entity = new CommentEntity();
        entity.setId(comment.id());
        entity.setProductId(comment.productId());
        entity.setUserId(comment.userId());
        entity.setContent(comment.content());
        entity.setRating(comment.rating());
        entity.setVisible(comment.isVisible());
        entity.setIdempotencyKey(comment.idempotencyKey());
        entity.setCreatedAt(comment.createdAt());
        entity.setUpdatedAt(comment.updatedAt());
        return entity;
    }

    private Comment toDomain(CommentEntity entity) {
        return Comment.restore(
            entity.getId(),
            entity.getProductId(),
            entity.getUserId(),
            entity.getContent(),
            entity.getRating(),
            entity.isVisible(),
            entity.getIdempotencyKey(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
}