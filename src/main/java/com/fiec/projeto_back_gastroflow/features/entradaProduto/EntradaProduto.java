package com.fiec.projeto_back_gastroflow.features.entradaProduto;

import com.fiec.projeto_back_gastroflow.features.entrada.models.Entrada;
import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "entrada_produto")
public class EntradaProduto {

    @EmbeddedId
    private EntradaProdutoId id;

    private Integer quantidade;

    private BigDecimal preco;

    @ManyToOne(fetch = FetchType.LAZY)

    @MapsId("entradaId")
    @JoinColumn(name = "fk_entrada_id")
    private Entrada entrada;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("produtoId") // Mapeia o atributo 'produtoId' da chave composta
    @JoinColumn(name = "fk_produto_id")
    private Produto produto;

}
