package com.fiec.projeto_back_gastroflow.features.user.services;


import com.fiec.projeto_back_gastroflow.features.firebase.models.dto.FcmTokenRequest;
import com.fiec.projeto_back_gastroflow.features.user.dto.*;
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
    CreatedUserResponseDto saveAdmin(RegisterAdminDto registerAdminDto);
    CreatedUserResponseDto saveStandard(RegisterStandardDto registerStandardDto);
    CreatedUserResponseDto saveGuest(RegisterGuestDto registerGuestDto);
    void deleteById(UUID id);
    MyUserDto getMe(User user);
    User updateFcmToken(UUID userId, FcmTokenRequest request);
}