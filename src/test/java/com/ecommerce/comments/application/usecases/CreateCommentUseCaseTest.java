package com.ecommerce.comments.application.usecases;

import com.ecommerce.comments.domain.exceptions.CommentValidationException;
import com.ecommerce.comments.domain.exceptions.DuplicateCommentException;
import com.ecommerce.comments.domain.exceptions.StorageException;
import com.ecommerce.comments.domain.model.Comment;
import com.ecommerce.comments.domain.ports.CommentRepository;
import com.ecommerce.comments.domain.ports.CommentValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCommentUseCaseTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentValidator commentValidator;

    private CreateCommentUseCase createCommentUseCase;

    @BeforeEach
    void setUp() {
        createCommentUseCase = new CreateCommentUseCase(commentRepository, commentValidator);
    }

    @Test
    void createComment_WithValidData_ShouldReturnSavedComment() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "Excelente producto, muy recomendado.";
        Integer rating = 5;

        Comment savedComment = Comment.create(productId, userId, content, rating);
        when(commentRepository.findByProductIdAndUserId(productId, userId)).thenReturn(Optional.empty());
        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        Comment result = createCommentUseCase.execute(productId, userId, content, rating);

        assertNotNull(result);
        verify(commentValidator).validateCommentContent(any(Comment.class));
        verify(commentValidator).validateCommentUniqueness(productId, userId);
        verify(commentValidator).validateCommentRating(rating);
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void createComment_WithExistingComment_ShouldThrowDuplicateCommentException() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "Muy buen producto";
        Integer rating = 4;

        Comment existingComment = Comment.create(productId, userId, content, rating);
        when(commentRepository.findByProductIdAndUserId(productId, userId))
            .thenReturn(Optional.of(existingComment));

        assertThrows(DuplicateCommentException.class, () ->
            createCommentUseCase.execute(productId, userId, content, rating)
        );

        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void createComment_WithInvalidContent_ShouldThrowCommentValidationException() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "Contenido inapropiado";
        Integer rating = 3;

        doThrow(new CommentValidationException("El contenido no es apropiado"))
            .when(commentValidator).validateCommentContent(any(Comment.class));

        assertThrows(CommentValidationException.class, () ->
            createCommentUseCase.execute(productId, userId, content, rating)
        );

        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void createComment_WithInvalidRating_ShouldThrowCommentValidationException() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "Buen producto";
        Integer rating = 6;

        doThrow(new CommentValidationException("La calificación debe estar entre 1 y 5"))
            .when(commentValidator).validateCommentRating(rating);

        assertThrows(CommentValidationException.class, () ->
            createCommentUseCase.execute(productId, userId, content, rating)
        );

        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    void createComment_WhenStorageFails_ShouldThrowStorageException() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "Muy satisfecho con la compra";
        Integer rating = 5;

        when(commentRepository.findByProductIdAndUserId(productId, userId)).thenReturn(Optional.empty());
        when(commentRepository.save(any(Comment.class)))
            .thenThrow(new StorageException("Error al conectar con la base de datos"));

        assertThrows(StorageException.class, () ->
            createCommentUseCase.execute(productId, userId, content, rating)
        );
    }

    @Test
    void createComment_WithNullContent_ShouldThrowCommentValidationException() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = null;
        Integer rating = 4;

        doThrow(new CommentValidationException("El contenido no puede estar vacío"))
            .when(commentValidator).validateCommentContent(any(Comment.class));

        assertThrows(CommentValidationException.class, () ->
            createCommentUseCase.execute(productId, userId, content, rating)
        );
    }

    @Test
    void createComment_WithEmptyContent_ShouldThrowCommentValidationException() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "";
        Integer rating = 3;

        doThrow(new CommentValidationException("El contenido no puede estar vacío"))
            .when(commentValidator).validateCommentContent(any(Comment.class));

        assertThrows(CommentValidationException.class, () ->
            createCommentUseCase.execute(productId, userId, content, rating)
        );
    }

    @Test
    void createComment_WithNullRating_ShouldThrowCommentValidationException() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "Contenido válido";
        Integer rating = null;

        doThrow(new CommentValidationException("La calificación es obligatoria"))
            .when(commentValidator).validateCommentRating(rating);

        assertThrows(CommentValidationException.class, () ->
            createCommentUseCase.execute(productId, userId, content, rating)
        );
    }

    @Test
    void createComment_WithMinimumRating_ShouldSucceed() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "No me gustó nada";
        Integer rating = 1;

        Comment savedComment = Comment.create(productId, userId, content, rating);
        when(commentRepository.findByProductIdAndUserId(productId, userId)).thenReturn(Optional.empty());
        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        Comment result = createCommentUseCase.execute(productId, userId, content, rating);

        assertNotNull(result);
        verify(commentValidator).validateCommentRating(1);
    }

    @Test
    void createComment_WithMaximumRating_ShouldSucceed() {
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String content = "El mejor producto que he comprado";
        Integer rating = 5;

        Comment savedComment = Comment.create(productId, userId, content, rating);
        when(commentRepository.findByProductIdAndUserId(productId, userId)).thenReturn(Optional.empty());
        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        Comment result = createCommentUseCase.execute(productId, userId, content, rating);

        assertNotNull(result);
        verify(commentValidator).validateCommentRating(5);
    }
}