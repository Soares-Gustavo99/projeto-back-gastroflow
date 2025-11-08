package com.fiec.projeto_back_gastroflow.features.products.repositories.impl;

import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoSearch;
import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoSummaryDTO;
import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import com.fiec.projeto_back_gastroflow.features.products.models.SortOrder;
import com.fiec.projeto_back_gastroflow.features.products.repositories.ProdutoCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ProdutoCustomRepositoryImpl implements ProdutoCustomRepository {

    private final EntityManager entityManager;

    public ProdutoCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ProdutoSummaryDTO> findProdutos(ProdutoSearch produtoSearch) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProdutoSummaryDTO> cq = cb.createQuery(ProdutoSummaryDTO.class);
        Root<Produto> produto = cq.from(Produto.class);

        List<Predicate> predicates = new ArrayList<>();
        List<Predicate> havingPredicates = new ArrayList<>();

        // 2. Construção dinâmica dos filtros (WHERE)

        // Mantemos os filtros no WHERE, que funcionam antes do agrupamento
        // Filtro: Categoria (busca exata)
        if (produtoSearch.getCategoria() != null) {
            Predicate categoriaPredicate = cb.equal(produto.get("categoria"), produtoSearch.getCategoria());
            predicates.add(categoriaPredicate);
        }

        // Filtro: Unidade de medida (igualdade exata)
        if (produtoSearch.getUnidadeMedida() != null) {
            Predicate unidadePredicate = cb.equal(produto.get("unidadeMedida"), produtoSearch.getUnidadeMedida());
            predicates.add(unidadePredicate);
        }

        // Filtro: Validade (igualdade exata)
        if (produtoSearch.getValidade() != null) {
            Predicate validadePredicate = cb.equal(produto.get("validade"), produtoSearch.getValidade());
            predicates.add(validadePredicate);
        }

        // Filtro LIKE no nome
        if (produtoSearch.getNome() != null && !produtoSearch.getNome().trim().isEmpty()) {
            Predicate nomeLike = cb.like(
                    cb.upper(produto.get("nome")),
                    "%" + produtoSearch.getNome().toUpperCase().trim() + "%"
            );
            predicates.add(nomeLike);
        }

        // Combina os filtros restantes com AND (WHERE)
        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        // --- Lógica de GROUP BY e HAVING ---

        // 2.1. GROUP BY: Agrupando por todos os campos que definem o DTO
        cq.groupBy(
                produto.get("nome")
        );

        // 2.2. HAVING: Filtrando grupos pela contagem
        if (produtoSearch.getQuantidadeEstoque() != null) {
            Expression<Long> countExpression = cb.count(produto.get("nome"));
            Predicate havingPredicate = cb.equal(countExpression, produtoSearch.getQuantidadeEstoque().longValue());
            havingPredicates.add(havingPredicate);
        }

        // Combina os filtros HAVING com AND
        if (!havingPredicates.isEmpty()) {
            cq.having(cb.and(havingPredicates.toArray(new Predicate[0])));
        }

        // 2.3. SELECT: Projetando para o ProdutoSummaryDTO
        cq.select(cb.construct(
                ProdutoSummaryDTO.class,
                cb.min(produto.get("id")),
                produto.get("nome"),
                cb.min(produto.get("categoria")),               // Categoria (Agregada)
                cb.min(produto.get("unidadeMedida")),           // UnidadeMedida (Agregada)
                cb.min(produto.get("validade")),
                cb.count(produto),                                   // Mapeia para 'contagem'
                cb.sum(produto.get("quantidadeEstoque")) // Mapeia para 'quantidade'
        ));

        // 3. Ordenação dinâmica (ORDER BY) - Adaptada
        String sortBy = produtoSearch.getSortBy();
        SortOrder sortOrder = produtoSearch.getSortOrder() != null ? produtoSearch.getSortOrder() : SortOrder.ASC;

        if (sortBy != null && !sortBy.trim().isEmpty()) {
            String safeSortBy = getSafeSortField(sortBy);
            Expression<?> sortExpression;

            if (Arrays.asList("nome", "categoria", "unidadeMedida", "validade").contains(safeSortBy)) {
                sortExpression = produto.get(safeSortBy);
            } else if ("quantidade".equalsIgnoreCase(safeSortBy) || "quantidadeestoque".equalsIgnoreCase(safeSortBy)) {
                sortExpression = cb.sum(produto.get("quantidadeEstoque"));
            } else if ("count".equalsIgnoreCase(safeSortBy)) {
                sortExpression = cb.count(produto);
            } else {
                sortExpression = produto.get("nome");
            }

            Order order = (sortOrder == SortOrder.DESC)
                    ? cb.desc(sortExpression)
                    : cb.asc(sortExpression);

            cq.orderBy(order);
        } else {
            cq.orderBy(cb.asc(produto.get("nome")));
        }


        // 4. Execução da query
        TypedQuery<ProdutoSummaryDTO> query = entityManager.createQuery(cq);

        return query.getResultList();
    }

    // Mapeia campos permitidos para ordenação (segurança)
    private String getSafeSortField(String sortBy) {
        switch (sortBy.toLowerCase()) {
            case "nome":
                return "nome";
            case "categoria":
                return "categoria";
            case "unidademedida":
                return "unidadeMedida";
            case "validade":
                return "validade";
            case "quantidadeestoque":
            case "quantidade":
                return "quantidade";
            case "count":
                return "count";
            case "id":
            default:
                return "nome";
        }
    }
}