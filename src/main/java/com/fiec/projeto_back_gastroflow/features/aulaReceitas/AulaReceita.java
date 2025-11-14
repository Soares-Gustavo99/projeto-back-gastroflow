package com.fiec.projeto_back_gastroflow.features.aulaReceitas;

import com.fiec.projeto_back_gastroflow.features.aula.models.Aula;
import com.fiec.projeto_back_gastroflow.features.receita.models.Receita;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AulaReceita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Chave estrangeira para Aula
    @ManyToOne
    @JoinColumn(name = "fk_aula_id")
    private Aula aula;

    // Chave estrangeira para Receita
    @ManyToOne
    @JoinColumn(name = "fk_receita_id")
    private Receita receita;

    // A quantidade de vezes que a Receita será preparada/utilizada na Aula
    private Integer quantidade;
}
