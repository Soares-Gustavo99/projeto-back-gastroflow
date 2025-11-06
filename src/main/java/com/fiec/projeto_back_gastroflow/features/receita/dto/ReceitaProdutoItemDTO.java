package com.fiec.projeto_back_gastroflow.features.receita.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaProdutoItemDTO {

    // ID do Produto (Ingrediente)
    private Long produtoId;

    // A quantidade específica desse produto na receita
    private Integer quantidade;
}
