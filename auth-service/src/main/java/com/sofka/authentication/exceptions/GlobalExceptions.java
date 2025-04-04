package com.sofka.authentication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptions {
    @ExceptionHandler(DataNotFoundException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleDataNotFound(DataNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Registro no encontrado");
        response.put("message", ex.getMessage());

        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(response));
    }

    @ExceptionHandler(UsernameTakenException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleUsernameTaken(UsernameTakenException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Nombre de usuario no disponible");
        response.put("message", ex.getMessage());

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @ExceptionHandler(DniDuplicatedException.class)
    public Mono<ResponseEntity<String>> handleDniException(DniDuplicatedException ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleCredentialsException(UsernameTakenException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Credenciales inválidas");
        response.put("message", ex.getMessage());

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

}
