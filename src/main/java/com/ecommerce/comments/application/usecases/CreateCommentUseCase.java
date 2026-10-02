package com.ecommerce.comments.application.usecases;

import com.ecommerce.comments.domain.exceptions.CommentValidationException;
import com.ecommerce.comments.domain.exceptions.DuplicateCommentException;
import com.ecommerce.comments.domain.exceptions.StorageException;
import com.ecommerce.comments.domain.model.Comment;
import com.ecommerce.comments.domain.ports.CommentRepository;
import com.ecommerce.comments.domain.ports.CommentValidator;
import com.ecommerce.comments.infrastructure.api.dto.CreateCommentRequest;
import com.ecommerce.comments.infrastructure.api.dto.CommentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreateCommentUseCase {

    private static final Logger logger = LoggerFactory.getLogger(CreateCommentUseCase.class);

    private final CommentRepository commentRepository;
    private final CommentValidator commentValidator;

    public CreateCommentUseCase(CommentRepository commentRepository, CommentValidator commentValidator) {
        this.commentRepository = commentRepository;
        this.commentValidator = commentValidator;
    }

    @Transactional
    public CommentResponse execute(CreateCommentRequest request) {
        logger.info("Iniciando proceso de creacion de comentario para producto: {}", request.productId());

        UUID productId = UUID.fromString(request.productId());
        UUID userId = UUID.fromString(request.userId());

        validateInput(productId, userId, request.rating(), request.content());

        checkIdempotencyKey(productId, userId, request.idempotencyKey());

        Comment comment = Comment.create(productId, userId, request.content(), request.rating());

        if (!comment.isContentAppropriate()) {
            logger.warn("Contenido inappropriate detectado para producto: {}", productId);
            throw new CommentValidationException("El contenido del comentario contiene lenguaje inapropiado");
        }

        Comment savedComment = persistComment(comment);

        logger.info("Comentario creado exitosamente con ID: {}", savedComment.id());

        return mapToResponse(savedComment);
    }

    private void validateInput(UUID productId, UUID userId, Integer rating, String content) {
        commentValidator.validateCommentRating(rating);

        Comment tempComment = Comment.create(productId, userId, content, rating);
        commentValidator.validateCommentContent(tempComment);
    }

    private void checkIdempotencyKey(UUID productId, UUID userId, String idempotencyKey) {
        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            var existingComment = commentRepository.findByProductIdAndUserId(productId, userId);
            if (existingComment.isPresent()) {
                logger.info("Comentario duplicado detectado para clave de idempotencia: {}", idempotencyKey);
                throw new DuplicateCommentException("Ya existe un comentario para este producto del mismo usuario");
            }
        }
    }

    private Comment persistComment(Comment comment) {
        try {
            return commentRepository.save(comment);
        } catch (Exception e) {
            logger.error("Error al almacenar comentario: {}", e.getMessage(), e);
            throw new StorageException("Error al guardar el comentario en el sistema");
        }
    }

    private CommentResponse mapToResponse(Comment comment) {
        return new CommentResponse(
            comment.id().toString(),
            comment.productId().toString(),
            comment.userId().toString(),
            comment.content(),
            comment.rating(),
            comment.isVisible(),
            comment.createdAt().toString()
        );
    }
}