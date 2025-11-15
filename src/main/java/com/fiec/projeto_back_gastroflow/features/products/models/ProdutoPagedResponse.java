package com.fiec.projeto_back_gastroflow.features.products.models;

import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoDTO;
import lombok.Data;

import java.util.List;

@Data
public class ProdutoPagedResponse {

    private Long total;

    private List <Produto> produtos;
}
