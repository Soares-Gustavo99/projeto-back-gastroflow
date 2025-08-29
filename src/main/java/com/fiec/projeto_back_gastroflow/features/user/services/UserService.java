package com.fiec.projeto_back_gastroflow.features.user.services;


import com.fiec.projeto_back_gastroflow.features.user.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    User update(UUID id, User updatedUser);
    void deleteById(UUID id);
}