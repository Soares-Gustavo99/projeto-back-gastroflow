package com.fiec.projeto_back_gastroflow.features.receitaProduto;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

// Marca a classe como uma parte embutível de uma chave primária
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
// É obrigatório implementar Serializable para chaves embutidas
public class ReceitaProdutoId implements Serializable {

    // A chave primária da Receita (parte da chave composta)
    private Long receitaId;

    // A chave primária do Produto (parte da chave composta)
    private Long produtoId;
}