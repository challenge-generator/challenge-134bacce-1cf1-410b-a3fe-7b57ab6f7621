package com.ecommerce.comments.infrastructure.api;


import com.ecommerce.comments.application.usecases.CreateCommentUseCase;
import com.ecommerce.comments.domain.exceptions.CommentValidationException;
import com.ecommerce.comments.domain.exceptions.DuplicateCommentException;
import com.ecommerce.comments.domain.exceptions.StorageException;
import com.ecommerce.comments.domain.model.Comment;
import com.ecommerce.comments.infrastructure.api.dto.CreateCommentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private com.ecommerce.comments.application.usecases.CreateCommentUseCase createCommentUseCase;

    private CreateCommentRequest validRequest;
    private CreateCommentRequest invalidRequest;

    @BeforeEach
    void setUp() {
        validRequest = new CreateCommentRequest(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "Este es un comentario válido sobre el producto",
            5
        );

        invalidRequest = new CreateCommentRequest(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "",
            6
        );
    }

    @Test
    void createComment_WithValidRequest_ShouldReturn201AndComment() throws Exception {
        UUID commentId = UUID.randomUUID();
        Comment savedComment = Comment.create(
            validRequest.productId(),
            validRequest.userId(),
            validRequest.content(),
            validRequest.rating()
        );

        when(createCommentUseCase.execute(
            any(UUID.class),
            any(UUID.class),
            anyString(),
            anyInt()
        )).thenReturn(savedComment);

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.productId").value(validRequest.productId().toString()))
            .andExpect(jsonPath("$.userId").value(validRequest.userId().toString()))
            .andExpect(jsonPath("$.content").value(validRequest.content()))
            .andExpect(jsonPath("$.rating").value(validRequest.rating()));

        verify(createCommentUseCase, times(1)).execute(
            eq(validRequest.productId()),
            eq(validRequest.userId()),
            eq(validRequest.content()),
            eq(validRequest.rating())
        );
    }

    @Test
    void createComment_WithDuplicateComment_ShouldReturn409Conflict() throws Exception {
        when(createCommentUseCase.execute(
            any(UUID.class),
            any(UUID.class),
            anyString(),
            anyInt()
        )).thenThrow(new DuplicateCommentException("El usuario ya ha comentado este producto"));

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.error").value("CONFLICTO"))
            .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void createComment_WithInvalidContent_ShouldReturn400BadRequest() throws Exception {
        when(createCommentUseCase.execute(
            any(UUID.class),
            any(UUID.class),
            anyString(),
            anyInt()
        )).thenThrow(new CommentValidationException("El contenido del comentario no puede estar vacío"));

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("VALIDACION"))
            .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void createComment_WithInvalidRating_ShouldReturn400BadRequest() throws Exception {
        when(createCommentUseCase.execute(
            any(UUID.class),
            any(UUID.class),
            anyString(),
            anyInt()
        )).thenThrow(new CommentValidationException("La calificación debe estar entre 1 y 5"));

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("VALIDACION"));
    }

    @Test
    void createComment_WhenStorageFails_ShouldReturn500InternalServerError() throws Exception {
        when(createCommentUseCase.execute(
            any(UUID.class),
            any(UUID.class),
            anyString(),
            anyInt()
        )).thenThrow(new StorageException("Error de conexión con la base de datos"));

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.error").value("ERROR_INTERNO"))
            .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void createComment_WithMissingProductId_ShouldReturn400BadRequest() throws Exception {
        CreateCommentRequest requestWithoutProductId = new CreateCommentRequest(
            null,
            UUID.randomUUID(),
            "Contenido válido",
            4
        );

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestWithoutProductId)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("VALIDACION"));
    }

    @Test
    void createComment_WithMissingUserId_ShouldReturn400BadRequest() throws Exception {
        CreateCommentRequest requestWithoutUserId = new CreateCommentRequest(
            UUID.randomUUID(),
            null,
            "Contenido válido",
            4
        );

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestWithoutUserId)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("VALIDACION"));
    }

    @Test
    void createComment_WithMissingContent_ShouldReturn400BadRequest() throws Exception {
        CreateCommentRequest requestWithoutContent = new CreateCommentRequest(
            UUID.randomUUID(),
            UUID.randomUUID(),
            null,
            4
        );

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestWithoutContent)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("VALIDACION"));
    }

    @Test
    void createComment_WithMissingRating_ShouldReturn400BadRequest() throws Exception {
        CreateCommentRequest requestWithoutRating = new CreateCommentRequest(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "Contenido válido",
            null
        );

        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestWithoutRating)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("VALIDACION"));
    }

    @Test
    void createComment_WithEmptyBody_ShouldReturn400BadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void createComment_WithInvalidJson_ShouldReturn400BadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("not valid json"))
            .andExpect(status().isBadRequest());
    }
}