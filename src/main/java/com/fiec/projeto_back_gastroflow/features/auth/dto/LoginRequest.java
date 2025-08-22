package com.fiec.projeto_back_gastroflow.features.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}