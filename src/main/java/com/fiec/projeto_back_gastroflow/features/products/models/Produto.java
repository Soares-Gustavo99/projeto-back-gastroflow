package com.fiec.projeto_back_gastroflow.features.products.models;

import com.fiec.projeto_back_gastroflow.features.entradaProduto.EntradaProduto;
import com.fiec.projeto_back_gastroflow.features.receita.models.Receita;
import com.fiec.projeto_back_gastroflow.features.receitaProduto.ReceitaProduto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="Produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private String nome;

    @Column
    private Categoria categoria;

    @Column
    private Integer quantidadeEstoque;

    @Column
    private UnidadeMedida unidadeMedida;

    @Column
    private Date validade;

    @Column
    private String picture;

    // >>> MUDANÇA AQUI: Removido @ManyToMany(mappedBy = "produtos") <<<
    // Nova relação One-to-Many com a entidade de junção ReceitaProduto
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReceitaProduto> receitas = new ArrayList<>(); // Lista de ReceitaProduto

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntradaProduto> produtos = new ArrayList<>(); // Lista de ReceitaProduto



    public Produto(String nome, Categoria categoria, Integer quantidadeEstoque, UnidadeMedida unidadeMedida, Date validade) {
        setNome(nome);
        setCategoria(categoria);
        setQuantidadeEstoque(quantidadeEstoque);
        setUnidadeMedida(unidadeMedida);
        setValidade(validade);

    }
}
