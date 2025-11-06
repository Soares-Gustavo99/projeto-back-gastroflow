package com.fiec.projeto_back_gastroflow.features.receita.repositories;

import com.fiec.projeto_back_gastroflow.features.receitaProduto.ReceitaProduto;
import com.fiec.projeto_back_gastroflow.features.receitaProduto.ReceitaProdutoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaProdutoRepository extends JpaRepository<ReceitaProduto, ReceitaProdutoId> {
    // Métodos específicos se necessário, mas JpaRepository já é suficiente
}
