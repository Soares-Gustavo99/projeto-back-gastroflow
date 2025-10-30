package com.fiec.projeto_back_gastroflow.features.aula.repositories;

import com.fiec.projeto_back_gastroflow.features.aula.models.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {

}