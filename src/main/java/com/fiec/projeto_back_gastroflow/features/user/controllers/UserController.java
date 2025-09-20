package com.fiec.projeto_back_gastroflow.features.user.controllers;

import com.fiec.projeto_back_gastroflow.features.user.dto.CreatedUserResponseDto;
import com.fiec.projeto_back_gastroflow.features.user.dto.RegisterAdminDto;
import com.fiec.projeto_back_gastroflow.features.user.dto.RegisterGuestDto;
import com.fiec.projeto_back_gastroflow.features.user.dto.RegisterStandardDto;
import com.fiec.projeto_back_gastroflow.features.user.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
