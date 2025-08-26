package org.abrohamovich.littleshop.infrustructure.exception;

import org.abrohamovich.littleshop.domain.exception.DataPersistenceException;
import org.abrohamovich.littleshop.domain.exception.DuplicateEntryException;
import org.abrohamovich.littleshop.domain.exception.ModelNotFoundException;
import org.abrohamovich.littleshop.domain.exception.ModelValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;
    private UUID uuid;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();
    }

    @Test
    public void handleModelNotFoundException_ShouldReturnNotFoundStatus() {
        ModelNotFoundException ex = new ModelNotFoundException(uuid.toString());

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = globalExceptionHandler.handleModelNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(uuid.toString(), response.getBody().getMessage());
    }

    @Test
    public void handleModelValidationException_ShouldReturnBadRequestStatus() {
        ModelValidationException ex = new ModelValidationException(uuid.toString());

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = globalExceptionHandler.handleModelValidationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(uuid.toString(), response.getBody().getMessage());
    }

    @Test
    public void handleDuplicateEntryException_ShouldReturnConflictStatus() {
        DuplicateEntryException ex = new DuplicateEntryException(uuid.toString());

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = globalExceptionHandler.handleDuplicateEntryException(ex);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(uuid.toString(), response.getBody().getMessage());
    }

    @Test
    public void handleDataPersistenceException_ShouldReturnInternalServerError() {
        DataPersistenceException ex = new DataPersistenceException(uuid.toString());

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = globalExceptionHandler.handleDataPersistenceException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("A database error occurred: " + uuid.toString(), response.getBody().getMessage());
    }

    @Test
    void handleMethodArgumentNotValidException_ShouldReturnBadRequestWithErrorsMap() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("defaultMessage", response.getBody().get("field"));
    }

    @Test
    public void handleRuntimeException_ShouldReturnInternalServerError() {
        RuntimeException ex = new RuntimeException(uuid.toString());

        ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = globalExceptionHandler.handleRuntimeException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("An unexpected error occurred: " + uuid.toString(), response.getBody().getMessage());
    }

}