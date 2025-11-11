package com.fiec.projeto_back_gastroflow.exceptions;

// Estender RuntimeException garante o Rollback automático na @Transactional
public class EstoqueInsuficienteException extends RuntimeException {
    public EstoqueInsuficienteException(String message) {
        super(message);
    }
}
