package com.fiec.projeto_back_gastroflow.features.receitaProduto;

import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import com.fiec.projeto_back_gastroflow.features.receita.models.Receita;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
// Nomeia a nova tabela relacional
@Table(name = "receita_produto")
public class ReceitaProduto {

    // Define a chave primária composta usando a classe ReceitaProdutoId
    @EmbeddedId
    private ReceitaProdutoId id;

    private String nomeProduto;

    // Campo adicional para a quantidade do produto na receita
    private Integer quantidade;

    // RELAÇÃO MANY-TO-ONE: Muitos ReceitaProduto para uma Receita
    @ManyToOne(fetch = FetchType.LAZY)
    // Indica a coluna de chave estrangeira no banco (fk_receita_id)
    @MapsId("receitaId") // Mapeia o atributo 'receitaId' da chave composta
    @JoinColumn(name = "fk_receita_id")
    private Receita receita;

    // RELAÇÃO MANY-TO-ONE: Muitos ReceitaProduto para um Produto
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("produtoId") // Mapeia o atributo 'produtoId' da chave composta
    @JoinColumn(name = "fk_produto_id")
    private Produto produto;
}
