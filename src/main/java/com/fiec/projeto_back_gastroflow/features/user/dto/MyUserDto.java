package com.fiec.projeto_back_gastroflow.features.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyUserDto {

    String nome;

    String email;

    String cnpj;

    String cpf;

    String tipo;

    String picture;

}
