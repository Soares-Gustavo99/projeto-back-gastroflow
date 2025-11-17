package com.fiec.projeto_back_gastroflow.features.aulaReceitas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AulaReceitaItemDTO {

    // ID da Receita
    private Long receitaId;

    private String nomeReceita;

    // A quantidade específica dessa receita na aula
    private Integer quantidade;
}
