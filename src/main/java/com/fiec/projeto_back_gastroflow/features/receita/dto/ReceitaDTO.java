package com.fiec.projeto_back_gastroflow.features.receita.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaDTO {
    private Long id;

    private String nome;

    private String descricao;

    private Integer tempoPreparo;

    private Integer rendimento;

    private String tipo;

    private Date dataAlteracao;

    private String usuarioAlteracao;

    private Date dataCadastro;

    private Integer professorReceita;


    private UUID userId;

    private List<Long> produtoIds; // apenas IDs, relacão N:N
}
