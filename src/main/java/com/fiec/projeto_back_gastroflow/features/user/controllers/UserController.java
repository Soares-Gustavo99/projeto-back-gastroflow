package com.fiec.projeto_back_gastroflow.features.user.controllers;

import com.fiec.projeto_back_gastroflow.features.user.dto.*;
import com.fiec.projeto_back_gastroflow.features.user.models.RegisterState;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import com.fiec.projeto_back_gastroflow.features.user.services.UserService;
import com.fiec.projeto_back_gastroflow.utils.ImageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@Tag(name = "Usuários", description = "Gerenciamento de usuários e registro de diferentes tipos de conta.")
@RestController
@RequestMapping("/v1/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Registra um novo Administrador", description = "Cria um usuário com a permissão 'ROLE_ADMIN'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador registrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de registro inválidos."),
            @ApiResponse(responseCode = "409", description = "Usuário já existe (email em uso).")
    })
    @PostMapping("/admin")
    public CreatedUserResponseDto registerAdmin(@Valid @RequestBody RegisterAdminDto registerAdminDto) throws Exception {
        return userService.saveAdmin(registerAdminDto);
    }


    @Operation(summary = "Registra um novo usuário Padrão", description = "Cria um usuário com a permissão 'ROLE_STANDARD'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário Padrão registrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de registro inválidos."),
            @ApiResponse(responseCode = "409", description = "Usuário já existe (email em uso).")
    })
    @PostMapping("/standard")
    public CreatedUserResponseDto registerStandard(@Valid @RequestBody RegisterStandardDto registerStandardDto){
        return userService.saveStandard(registerStandardDto);
    }

    @Operation(summary = "Registra um novo usuário Convidado", description = "Cria um usuário com a permissão 'ROLE_GUEST'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário Convidado registrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de registro inválidos."),
            @ApiResponse(responseCode = "409", description = "Usuário já existe (email em uso).")
    })
    @PostMapping("/guest")
    public CreatedUserResponseDto registerGuest(@Valid @RequestBody RegisterGuestDto registerGuestDto){
        return userService.saveGuest(registerGuestDto);
    }

    @Operation(summary = "Obtém dados do usuário logado", description = "Retorna os dados do próprio usuário autenticado ('me').")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do usuário retornados com sucesso."),
            @ApiResponse(responseCode = "401", description = "Não autenticado.")
    })
    @GetMapping("/me")
    public MyUserDto getMe(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return userService.getMe(user);
    }

    @Operation(summary = "Insere/Atualiza a foto de perfil do usuário", description = "Endpoint para upload de imagem de perfil.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Foto de perfil atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (e.g., arquivo não enviado)."),
            @ApiResponse(responseCode = "401", description = "Não autenticado.")
    })
    // @RequestParam("image") é um parâmetro de formulário (body/query)
    @Parameter(name = "image", description = "Arquivo da imagem a ser enviada.", required = true, in = ParameterIn.QUERY)
    @PutMapping("/photo")
    public void insertUserImage(@RequestParam("image") MultipartFile image, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        String imageName = ImageUtils.saveImage(image);
        user.setPicture(imageName);
        user.setState(RegisterState.IMAGE_CREATED);
        userService.save(user);
    }



}
