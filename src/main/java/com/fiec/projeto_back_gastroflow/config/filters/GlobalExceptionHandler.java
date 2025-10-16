package com.fiec.projeto_back_gastroflow.config.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Componente que centraliza o tratamento de exceções (Advice) para todos os Controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * Trata e loga exceções gerais e não tratadas (Uncaught Exceptions).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {

        // Loga a exceção completa, incluindo o Stack Trace, para análise.
        // O Log4j2 salvará isso no arquivo (como configurado no log4j2.xml).
        log.error("!!!!!! EXCEÇÃO GLOBAL CAPTURADA !!!!!!", ex);

        // Estrutura de resposta simples para o cliente (JSON)
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro interno. Por favor, tente novamente mais tarde."
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Você pode adicionar métodos específicos para exceções personalizadas (Ex: ResourceNotFoundException)
    // @ExceptionHandler(ResourceNotFoundException.class)
    // public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex) {
    //     log.warn("Recurso não encontrado: {}", ex.getMessage());
    //     // ... retorna 404
    // }

    /**
     * Classe DTO (Data Transfer Object) simples para o corpo da resposta de erro.
     */
    private static class ErrorResponse {
        public int status;
        public String message;

        public ErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}
