package com.fiec.projeto_back_gastroflow.features.user.controllers;

import com.fiec.projeto_back_gastroflow.features.user.dto.*;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import com.fiec.projeto_back_gastroflow.features.user.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/admin")
    public CreatedUserResponseDto registerAdmin(@Valid @RequestBody RegisterAdminDto registerAdminDto) throws Exception {
        return userService.saveAdmin(registerAdminDto);
    }

    @PostMapping("/standard")
    public CreatedUserResponseDto registerStandard(@Valid @RequestBody RegisterStandardDto registerStandardDto){
        return userService.saveStandard(registerStandardDto);
    }

    @PostMapping("/guest")
    public void registerGuest(@Valid @RequestBody RegisterGuestDto registerGuestDto){

    }

    @GetMapping("/me")
    public MyUserDto getMe(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return userService.getMe(user);
    }
}
