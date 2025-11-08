package com.fiec.projeto_back_gastroflow.features.products.dto;

import com.fiec.projeto_back_gastroflow.features.products.models.Categoria;
import com.fiec.projeto_back_gastroflow.features.products.models.UnidadeMedida;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDetailsDTO {

    private Long id;

    private String nome;

    private UnidadeMedida unidadeMedida;

    private Categoria categoria;

    private Integer quantidadeEstoque;

    private String imagem;


}
