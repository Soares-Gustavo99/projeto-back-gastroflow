package com.fiec.projeto_back_gastroflow.features.entrada.models;

import com.fiec.projeto_back_gastroflow.features.entradaProduto.EntradaProduto;
import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import com.fiec.projeto_back_gastroflow.features.receitaProduto.ReceitaProduto;
import com.fiec.projeto_back_gastroflow.features.supplier.models.Supplier;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Entradas")
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id; // ID como UUID, seguindo o padrão de User e Supplier

    @Column(nullable = false)
    private Date dataEntrada;

    @Column
    private String observacao;

    @Column
    private Long preco;


    // Relação ManyToOne com Fornecedor (Supplier tem id UUID)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = true) // Assumindo que o fornecedor pode ser opcional
    private Supplier fornecedor;

    // Relação ManyToOne com Usuário (User tem id UUID)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



    @OneToMany(mappedBy = "entrada", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntradaProduto> produtos = new ArrayList<>(); // Lista de EntradaProduto


    public Entrada(Date dataEntrada, String observacao, Supplier fornecedor, Long preco, User user) {
        this.dataEntrada = dataEntrada;
        this.observacao = observacao;
        this.fornecedor = fornecedor;
        this.preco = preco;
        this.user = user; }

}