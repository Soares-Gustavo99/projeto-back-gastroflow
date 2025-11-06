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

    private String nome;

    private String descricao;

    private Integer tempoPreparo;

    private Integer rendimento;

    private String tipo;

    private Date dataAlteracao;

    private String usuarioAlteracao;

    private Date dataCadastro;

    private String professorReceita;


    private UUID userId;

    // >>> MUDANÇA CRÍTICA AQUI <<<
    // Substituído List<Long> produtoIds por List<ReceitaProdutoItemDTO>
    private List<ReceitaProdutoItemDTO> produtos;
}
