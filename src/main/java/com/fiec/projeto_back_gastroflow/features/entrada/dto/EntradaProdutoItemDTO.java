package com.fiec.projeto_back_gastroflow.features.entrada.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntradaProdutoItemDTO {

    private Long produtoId;

    private Integer quantidade;

    private Long preco;
}
