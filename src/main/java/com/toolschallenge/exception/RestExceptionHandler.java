package com.toolschallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global Exception Handler (ControllerAdvice)
 * Responsável por interceptar exceções lançadas por Controllers ou Serviços
 * e formatar a resposta HTTP de erro de maneira padronizada e legível.
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Trata exceções do tipo ResponseStatusException (lançadas nos services).
     * Garante que a mensagem de erro personalizada seja exibida no JSON.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {

        // 1. Constrói o corpo da resposta de erro (JSON)
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", ex.getStatusCode().value());
        body.put("error", HttpStatus.valueOf(ex.getStatusCode().value()).getReasonPhrase());
        body.put("message", ex.getReason());

        // 4. Retorna a resposta com o corpo JSON e o Status Code correto (e.g., 400 Bad Request)
        return new ResponseEntity<>(body, ex.getStatusCode());
    }
}
