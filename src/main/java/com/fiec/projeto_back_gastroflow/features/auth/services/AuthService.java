package com.fiec.projeto_back_gastroflow.features.auth.services;


import com.fiec.projeto_back_gastroflow.features.auth.dto.LoginRequest;
import com.fiec.projeto_back_gastroflow.features.auth.dto.RegisterRequest;
import com.fiec.projeto_back_gastroflow.features.user.models.User;

public interface AuthService {
    User register(RegisterRequest request);
    User login(LoginRequest request);
}