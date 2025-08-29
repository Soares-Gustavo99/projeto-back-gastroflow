package com.fiec.projeto_back_gastroflow.features.receita.repositories;

import com.fiec.projeto_back_gastroflow.features.receita.models.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    Optional<Receita> getByNome(String nome);
}

