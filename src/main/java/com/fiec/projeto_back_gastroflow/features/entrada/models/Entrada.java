package com.fiec.projeto_back_gastroflow.features.entrada.models;

import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import com.fiec.projeto_back_gastroflow.features.supplier.models.Supplier;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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

    @Column(nullable = false)
    private Integer quantidade;

    @Column
    private String observacao;

    // Relação ManyToOne com Produto (Produto tem Id Long)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    // Relação ManyToOne com Fornecedor (Supplier tem id UUID)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = true) // Assumindo que o fornecedor pode ser opcional
    private Supplier fornecedor;

    // Relação ManyToOne com Usuário (User tem id UUID)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Construtor sem o ID (para uso ao criar uma nova entrada)
    public Entrada(Date dataEntrada, Integer quantidade, Produto produto, Supplier fornecedor, User user) {
        this.dataEntrada = dataEntrada;
        this.quantidade = quantidade;
        this.observacao = observacao;
        this.produto = produto;
        this.fornecedor = fornecedor;
        this.user = user;
    }

    public Entrada(Date dataEntrada, Integer quantidade, String observacao, Produto produto, Supplier supplier, User user) {
    }
}