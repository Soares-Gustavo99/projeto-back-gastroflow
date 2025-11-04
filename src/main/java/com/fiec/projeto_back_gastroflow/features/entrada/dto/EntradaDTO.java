package com.fiec.projeto_back_gastroflow.features.entrada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntradaDTO {

    // ID da própria entrada (para retorno/update)
    private Long id;

    // Campos da Entrada
    private Date dataEntrada;
    private Integer quantidade;
    private String observacao;

    // Campos de Auditoria (baseado em ReceitaDTO.java)
    private Date dataCadastro;
    private Date dataAlteracao;

    // Chaves Estrangeiras (somente os IDs para criação/update)
    private Long produtoId; // ID do Produto é Long
    private Long fornecedorId; // Corrigido para UUID
    private UUID userId; // ID do Usuário é UUID
}