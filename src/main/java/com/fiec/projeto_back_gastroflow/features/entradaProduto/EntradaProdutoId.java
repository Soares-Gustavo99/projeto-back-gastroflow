package com.fiec.projeto_back_gastroflow.features.entradaProduto;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor

public class EntradaProdutoId implements Serializable {

    private Long entradaId;

    private Long produtoId;
}
