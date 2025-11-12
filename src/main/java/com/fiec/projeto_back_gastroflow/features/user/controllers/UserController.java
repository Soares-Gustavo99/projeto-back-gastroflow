package com.fiec.projeto_back_gastroflow.features.user.controllers;

import com.fiec.projeto_back_gastroflow.features.user.dto.*;
import com.fiec.projeto_back_gastroflow.features.user.models.RegisterState;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import com.fiec.projeto_back_gastroflow.features.user.services.UserService;
import com.fiec.projeto_back_gastroflow.utils.ImageUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public CreatedUserResponseDto registerGuest(@Valid @RequestBody RegisterGuestDto registerGuestDto){
        return userService.saveGuest(registerGuestDto);
    }

    @GetMapping("/me")
    public MyUserDto getMe(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return userService.getMe(user);
    }

//    COLOCAR ISSO DE VOLTA ASSIM QUE RESOLVER O FIREBASE
    @PutMapping("/photo")
    public void insertUserImage(@RequestParam("image") MultipartFile image, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        String imageName = ImageUtils.saveImage(image);
        user.setPicture(imageName);
        user.setState(RegisterState.IMAGE_CREATED);
        userService.save(user);
    }



}
