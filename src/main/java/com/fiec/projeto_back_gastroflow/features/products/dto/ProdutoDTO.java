package com.fiec.projeto_back_gastroflow.features.products.dto;

import com.fiec.projeto_back_gastroflow.features.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private String nome;

    private String categoria;

    private Integer quantidadeEstoque;

    private String unidadeMedida;

    private Date validade;
}


