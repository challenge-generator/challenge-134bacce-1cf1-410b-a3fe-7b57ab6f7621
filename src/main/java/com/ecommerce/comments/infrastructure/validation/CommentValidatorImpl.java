package com.ecommerce.comments.infrastructure.validation;


import com.ecommerce.comments.domain.ports.ValidationResult;
import com.ecommerce.comments.domain.exceptions.CommentValidationException;
import com.ecommerce.comments.domain.exceptions.DuplicateCommentException;
import com.ecommerce.comments.domain.model.Comment;
import com.ecommerce.comments.domain.ports.CommentRepository;
import com.ecommerce.comments.domain.ports.CommentValidator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class CommentValidatorImpl implements CommentValidator {

    private static final int MIN_CONTENT_LENGTH = 10;
    private static final int MAX_CONTENT_LENGTH = 2000;
    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;
    private static final Pattern INAPPROPRIATE_CONTENT_PATTERN = Pattern.compile(
        "(?i)(spam|scam|fraud|malicious|offensive|explicit|hate|threat)"
    );
    private static final List<String> FORBIDDEN_WORDS = Arrays.asList(
        "spam", "scam", "fraud", "malicious", "offensive"
    );

    private final CommentRepository commentRepository;

    public CommentValidatorImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void validateCommentContent(Comment comment) {
        String content = comment.content();
        
        if (content == null || content.isBlank()) {
            throw new CommentValidationException("El contenido del comentario no puede estar vacío");
        }
        
        if (content.length() < MIN_CONTENT_LENGTH) {
            throw new CommentValidationException(
                String.format("El contenido debe tener al menos %d caracteres", MIN_CONTENT_LENGTH)
            );
        }
        
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new CommentValidationException(
                String.format("El contenido no puede exceder %d caracteres", MAX_CONTENT_LENGTH)
            );
        }
        
        if (containsInappropriateContent(content)) {
            throw new CommentValidationException("El contenido contiene lenguaje inapropiado");
        }
        
        if (containsForbiddenWords(content)) {
            throw new CommentValidationException("El contenido contiene palabras prohibidas");
        }
        
        if (!hasValidStructure(content)) {
            throw new CommentValidationException("El contenido tiene una estructura inválida");
        }
    }

    @Override
    public void validateCommentUniqueness(UUID productId, UUID userId) {
        if (productId == null) {
            throw new CommentValidationException("El ID del producto es obligatorio");
        }
        
        if (userId == null) {
            throw new CommentValidationException("El ID del usuario es obligatorio");
        }
        
        var existingComment = commentRepository.findByProductIdAndUserId(productId, userId);
        
        if (existingComment.isPresent()) {
            throw new DuplicateCommentException(
                "Ya existe un comentario para este producto del mismo usuario"
            );
        }
    }

    @Override
    public void validateCommentRating(Integer rating) {
        if (rating == null) {
            throw new CommentValidationException("La calificación es obligatoria");
        }
        
        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new CommentValidationException(
                String.format(
                    "La calificación debe estar entre %d y %d", 
                    MIN_RATING, 
                    MAX_RATING
                )
            );
        }
    }

    @Override
    public ValidationResult validateComment(Comment comment) {
        try {
            validateCommentContent(comment);
            validateCommentUniqueness(comment.productId(), comment.userId());
            validateCommentRating(comment.rating());
            return ValidationResult.success();
        } catch (CommentValidationException | DuplicateCommentException e) {
            return ValidationResult.failure(e.getMessage());
        }
    }

    private boolean containsInappropriateContent(String content) {
        return INAPPROPRIATE_CONTENT_PATTERN.matcher(content).find();
    }

    private boolean containsForbiddenWords(String content) {
        String lowerContent = content.toLowerCase();
        return FORBIDDEN_WORDS.stream()
            .anyMatch(word -> lowerContent.contains(word.toLowerCase()));
    }

    private boolean hasValidStructure(String content) {
        String trimmed = content.trim();
        
        if (!Character.isLetterOrDigit(trimmed.charAt(0))) {
            return false;
        }
        
        if (!Character.isLetterOrDigit(trimmed.charAt(trimmed.length() - 1))) {
            return false;
        }
        
        long letterCount = content.chars()
            .filter(Character::isLetter)
            .count();
        
        return letterCount >= content.length() * 0.5;
    }
}