package com.fiec.projeto_back_gastroflow.features.products.dto;

import com.fiec.projeto_back_gastroflow.features.products.models.Categoria;
import com.fiec.projeto_back_gastroflow.features.products.models.UnidadeMedida;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;

    private String nome;

    private Categoria categoria;

    private Integer quantidadeEstoque;

    private UnidadeMedida unidadeMedida;

    private Date validade;


}
