package com.fiec.projeto_back_gastroflow.features.entrada.models;

import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import com.fiec.projeto_back_gastroflow.features.supplier.models.Supplier;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private Integer quantidade;

    @Column
    private String observacao;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Produto> produtos;


    @ManyToOne
    private Supplier fornecedor;

    // Relação ManyToOne com Usuário (User tem id UUID)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


}