package com.fiec.projeto_back_gastroflow.features.entrada.dto;

import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
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
    private Integer quantidade;
    private String observacao;
    private List<Long> produtoIds; // ID do Produto é Long
    private Long fornecedorId; // ID do Fornecedor é UUID
    private UUID userId; // ID do Usuário é UUID

    // O ID da própria entrada pode ser incluído se o DTO for usado para retorno
    private Long id;


}