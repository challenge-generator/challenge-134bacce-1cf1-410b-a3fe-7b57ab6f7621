package com.ecommerce.comments.infrastructure.api;

import com.ecommerce.comments.application.usecases.CreateCommentUseCase;
import com.ecommerce.comments.domain.exceptions.CommentValidationException;
import com.ecommerce.comments.domain.exceptions.DuplicateCommentException;
import com.ecommerce.comments.domain.exceptions.StorageException;
import com.ecommerce.comments.infrastructure.api.dto.CommentResponse;
import com.ecommerce.comments.infrastructure.api.dto.CreateCommentRequest;
import com.ecommerce.comments.infrastructure.api.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
@Tag(name = "Comentarios", description = "API para gestion de comentarios de productos")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private final CreateCommentUseCase createCommentUseCase;

    public CommentController(CreateCommentUseCase createCommentUseCase) {
        this.createCommentUseCase = createCommentUseCase;
    }

    @PostMapping
    @Operation(
        summary = "Crear un nuevo comentario",
        description = "Permite a un usuario crear un comentario sobre un producto. " +
                      "El sistema valida el contenido y almacena el comentario de manera idempotente."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Comentario creado exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentResponse.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada invalidos o contenido inapropiado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Ya existe un comentario para este producto del usuario",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    public ResponseEntity<CommentResponse> createComment(
            @Parameter(description = "Datos del comentario a crear", required = true)
            @Valid @RequestBody CreateCommentRequest request) {

        logger.info("Received comment creation request for product: {}", request.productId());

        try {
            CommentResponse response = createCommentUseCase.execute(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (CommentValidationException e) {
            logger.warn("Validation error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);

        } catch (DuplicateCommentException e) {
            logger.warn("Duplicate comment error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(null);

        } catch (StorageException e) {
            logger.error("Storage error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }
}