package com.fiec.projeto_back_gastroflow.features.auth.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String picture;
    // Opcionalmente, pode-se incluir o nível de acesso
    // private UserLevel accessLevel;
}