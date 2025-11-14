package com.fiec.projeto_back_gastroflow.features.aulaReceitas.repositories;

import com.fiec.projeto_back_gastroflow.features.aulaReceitas.AulaReceita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AulaReceitaRepository extends JpaRepository<AulaReceita, Long> {
}
