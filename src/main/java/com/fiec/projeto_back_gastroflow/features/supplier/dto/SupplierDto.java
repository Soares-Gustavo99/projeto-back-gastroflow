package com.fiec.projeto_back_gastroflow.features.supplier.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SupplierDto {

    @NotBlank(message = "A Razão Social não pode estar vazia.")
    @Size(min = 3, message = "A Razão Social deve ter no mínimo 3 caracteres.")
    private String razaoSocial;

    @Size(min = 3, message = "O Nome Fantasia deve ter no mínimo 3 caracteres.")
    private String nomeFantasia;

    @NotBlank(message = "O Telefone não pode estar vazio.")
    @Size(min = 8, message = "O Telefone deve ter no mínimo 8 dígitos.")
    private String telefone;

    @Email(message = "Por favor, forneça um endereço de email válido.")
    private String email;

    @NotBlank(message = "O Endereço não pode estar vazio.")
    @Size(min = 5, message = "O Endereço deve ter no mínimo 5 caracteres.")
    private String endereco;
}