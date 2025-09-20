package com.fiec.projeto_back_gastroflow.features.admin.controllers;

import com.fiec.projeto_back_gastroflow.features.user.models.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/admin")
public class AdminController {

    @Secured("ADMIN")
    @GetMapping
    public void testRequest(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        System.out.println(user);

    }
}
