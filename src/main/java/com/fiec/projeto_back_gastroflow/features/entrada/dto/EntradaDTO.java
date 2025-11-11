package com.fiec.projeto_back_gastroflow.features.entrada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntradaDTO {

    // O DTO deve conter os campos para recebimento de dados
    // E os IDs para as chaves estrangeiras
    private Date dataEntrada;
    private String observacao;
    private Long fornecedorId; // ID do Fornecedor é UUID

    private List<EntradaProdutoItemDTO> produtos;

    // O ID da própria entrada pode ser incluído se o DTO for usado para retorno
    private Long id;


    public EntradaDTO(Date dataEntrada, String observacao, Long fornecedorId, UUID userId, Long id) {
        this.dataEntrada = dataEntrada;
        this.observacao = observacao;
        this.fornecedorId = fornecedorId;
        this.id = id;
    }

}