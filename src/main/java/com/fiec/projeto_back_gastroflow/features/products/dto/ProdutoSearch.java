package com.fiec.projeto_back_gastroflow.features.products.dto;


import com.fiec.projeto_back_gastroflow.features.products.models.Categoria;
import com.fiec.projeto_back_gastroflow.features.products.models.SortOrder;
import com.fiec.projeto_back_gastroflow.features.products.models.UnidadeMedida;
import lombok.Data;

import java.util.Date;

@Data
public class ProdutoSearch {

    private String nome;

    private Categoria categoria;

    private Integer quantidadeEstoque;

    private UnidadeMedida unidadeMedida;

    private Date validade;

    private SortOrder sortOrder;

    private String sortBy;
}
