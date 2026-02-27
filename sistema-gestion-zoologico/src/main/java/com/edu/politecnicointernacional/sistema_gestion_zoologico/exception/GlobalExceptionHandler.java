package com.edu.politecnicointernacional.sistema_gestion_zoologico.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

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
}
