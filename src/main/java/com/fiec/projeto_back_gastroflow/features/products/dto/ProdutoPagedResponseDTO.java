package com.fiec.projeto_back_gastroflow.features.products.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProdutoPagedResponseDTO {

    private Long total;

    private Long totalPages;

    private List <ProdutoDTO> produtos;
}
