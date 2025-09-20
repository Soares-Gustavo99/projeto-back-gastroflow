package com.fiec.projeto_back_gastroflow.features.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class UserDto {
    @Email(message = "Please provide a valid email address")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
            message = "A senha deve ter no mínimo 8 caracteres, " +
                    "incluindo pelo menos uma letra maiúscula e um caractere especial.")
    private String password;

    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres.")
    private String name;
}
