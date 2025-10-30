package com.fiec.projeto_back_gastroflow.features.retirada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetiradaDTO {

    // ID da própria retirada (para retorno/update)
    private Long id;

    // Campos da Retirada
    private Date dataRetirada;
    private Integer quantidade;
    private String observacao;

    // Chaves Estrangeiras (somente os IDs)
    private Long produtoId; // Long
    private UUID userId; // UUID
}