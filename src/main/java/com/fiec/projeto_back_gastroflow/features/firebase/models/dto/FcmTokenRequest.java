package com.fiec.projeto_back_gastroflow.features.firebase.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data; // Se estiver usando Lombok

/**
 * DTO para receber o token FCM do cliente.
 */
@Data // Adiciona getters, setters, toString (Lombok)
public class FcmTokenRequest {

    // O token é o dado principal e deve ser obrigatório.
    @NotBlank(message = "O token FCM não pode ser vazio.")
    private String fcmToken;

    // Você pode incluir o ID do usuário aqui,
    // mas o ideal é obter o ID do token de autenticação (JWT) do usuário logado.
    // private Long userId;
}
