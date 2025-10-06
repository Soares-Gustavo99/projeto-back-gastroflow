package com.fiec.projeto_back_gastroflow.features.auth.services.impl;

import com.fiec.projeto_back_gastroflow.features.auth.dto.LoginRequest;
import com.fiec.projeto_back_gastroflow.features.auth.dto.RegisterRequest;
import com.fiec.projeto_back_gastroflow.features.auth.services.AuthService;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import com.fiec.projeto_back_gastroflow.features.user.models.UserLevel;
import com.fiec.projeto_back_gastroflow.features.user.repositories.UserRepository;
import com.fiec.projeto_back_gastroflow.features.user.services.UserService;
import com.fiec.projeto_back_gastroflow.utils.PasswordEncryptor;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User register(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAccessLevel(UserLevel.ROLE_STANDARD); // Define o nível de acesso padrão
        user.setPicture(request.getPicture());

        return userRepository.save(user);
    }

    @Override
    public User login(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .filter(user -> PasswordEncryptor.getInstance().matches(request.getPassword(), user.getPassword()))
                .orElseThrow(() -> new BadCredentialsException("Email ou senha inválidos."));
    }
}