package com.edu.politecnicointernacional.sistema_gestion_zoologico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler that provides consistent error responses across all controllers.
 * Validation errors are handled here centrally, removing the need for BindingResult in each controller.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AnimalExceptionValorNoEncontado.class, AlimentacionExceptionNotFound.class,
            CitaMedicaExceptionNotFound.class, UsuarioExceptionNotFound.class})
    public ResponseEntity<Map<String, String>> handleNotFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler({AnimalExceptionIsPresent.class, UsuarioExceptionEmailDuplicado.class})
    public ResponseEntity<Map<String, String>> handleConflict(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler({AnimalExceptionNotNull.class, AlimentacionExceptionNotNull.class,
            CitaMedicaExceptionNotNull.class, UsuarioExceptionNotNull.class})
    public ResponseEntity<Map<String, String>> handleBadRequest(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    /**
     * Handles Bean Validation errors (@Valid), replacing the per-controller BindingResult pattern.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errores.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Error interno del servidor. Por favor contacte al administrador."));
    }
}
