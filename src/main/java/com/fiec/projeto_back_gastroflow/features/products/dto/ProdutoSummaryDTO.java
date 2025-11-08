package com.fiec.projeto_back_gastroflow.features.products.dto;

import com.fiec.projeto_back_gastroflow.features.products.models.Categoria;
import com.fiec.projeto_back_gastroflow.features.products.models.UnidadeMedida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate; // Supondo que validade é LocalDate
import java.util.Date;

/**
 * DTO para resumir o resultado da busca customizada com GROUP BY.
 * Agora agrupa por todos os atributos que definem o produto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoSummaryDTO {

    private Long id;
    private String nome;
    private Categoria categoria;         // Novo atributo
    private UnidadeMedida unidadeMedida;     // Novo atributo
    private Date validade;       // Novo atributo (Ajuste o tipo se necessário)
    private Long contagem;
    private Integer quantidadeEstoque;


}