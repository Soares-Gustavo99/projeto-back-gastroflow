package com.fiec.projeto_back_gastroflow.features.aula.models;

import com.fiec.projeto_back_gastroflow.features.aulaReceitas.AulaReceita;
import com.fiec.projeto_back_gastroflow.features.receita.models.Receita;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private Date data;

    private String instrutor;

    private String materia;

    private Integer ano;

    private Integer semestre;

    private Integer modulo;

    private String periodo;



    @OneToMany(mappedBy = "aula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AulaReceita> receitas = new ArrayList<>(); // Agora é uma lista de AulaReceita

    @ManyToOne
    @JoinColumn(name = "fk_usuario_id")
    private User user;

}
