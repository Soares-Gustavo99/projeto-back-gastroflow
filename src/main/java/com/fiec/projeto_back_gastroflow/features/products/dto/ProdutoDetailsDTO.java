package com.fiec.projeto_back_gastroflow.features.products.dto;

import com.fiec.projeto_back_gastroflow.features.products.models.Categoria;
import com.fiec.projeto_back_gastroflow.features.products.models.UnidadeMedida;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDetailsDTO {

    private String nome;

    private UnidadeMedida unidadeMedida;

    private Categoria categoria;

    private String imagem;

    private Integer contagem;

    private List<ProdutoLista> produtos;

    static class ProdutoLista {

        private Long id;

        private Integer quantidadeEstoque;

        private Date validade;

        private Long entradaId;
    }
}
