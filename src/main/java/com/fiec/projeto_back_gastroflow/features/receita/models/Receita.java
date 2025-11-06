package com.fiec.projeto_back_gastroflow.features.receita.models;

import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import com.fiec.projeto_back_gastroflow.features.receitaProduto.ReceitaProduto;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private Integer tempoPreparo;
    private Integer rendimento;
    private String tipo;

    @UpdateTimestamp
    private Date dataAlteracao;

    private String usuarioAlteracao;

    @CreationTimestamp
    private Date dataCadastro;

    private String professorReceita;

    @ManyToOne
    @JoinColumn(name = "fk_usuario_id") // nome da coluna no banco
    private User user;

    // >>> MUDANÇA AQUI: Removido @ManyToMany <<<
    // Nova relação One-to-Many com a entidade de junção ReceitaProduto
    @OneToMany(mappedBy = "receita", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReceitaProduto> produtos = new ArrayList<>(); // Lista de ReceitaProduto
}
