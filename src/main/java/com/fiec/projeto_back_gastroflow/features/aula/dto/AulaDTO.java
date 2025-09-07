package com.fiec.projeto_back_gastroflow.features.aula.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AulaDTO {

    private Long id;

    private String nome;
    private String descricao;
    private Date data;
    private String instrutor;
    private String materia;

    private List<Long> ReceitaIds;//Relação N:N
}
