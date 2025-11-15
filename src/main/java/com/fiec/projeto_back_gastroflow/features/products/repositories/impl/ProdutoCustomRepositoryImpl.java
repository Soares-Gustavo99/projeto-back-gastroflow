package com.fiec.projeto_back_gastroflow.features.products.repositories.impl;

import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoSearch;
import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import com.fiec.projeto_back_gastroflow.features.products.models.ProdutoPagedResponse;
import com.fiec.projeto_back_gastroflow.features.products.models.SortOrder;
import com.fiec.projeto_back_gastroflow.features.products.repositories.ProdutoCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProdutoCustomRepositoryImpl implements ProdutoCustomRepository {

    private final EntityManager entityManager;

    // Construtor para injeção do EntityManager
    public ProdutoCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Busca e pagina produtos customizadamente usando a Criteria API do JPA.
     * @param produtoSearch Objeto com os critérios de busca, ordenação e paginação.
     * @return Objeto Page<Produto> contendo a lista e os metadados de paginação.
     */
    @Override
    public Page<Produto> findProdutos(ProdutoSearch produtoSearch) {

        // 1. Definição da Paginação
        Pageable pageable = PageRequest.of(produtoSearch.getPageNumber(), produtoSearch.getPageSize());

        // 2. Consulta de CONTAGEM (Total de Elementos)
        Long totalElements = countProdutos(produtoSearch);

        if (totalElements == 0) {
            return Page.empty(pageable); // Retorna página vazia se não houver resultados
        }

        // 3. Consulta de CONTEÚDO (Lista de Produtos)
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> cq = cb.createQuery(Produto.class);
        Root<Produto> produto = cq.from(Produto.class);

        // Aplica os predicados (WHERE) e ordenação (ORDER BY)
        List<Predicate> predicates = buildPredicates(cb, produto, produtoSearch);
        cq.where(predicates.toArray(new Predicate[0]));
        applyOrder(cb, cq, produto, produtoSearch);

        // 4. Execução da query de CONTEÚDO com LIMIT e OFFSET
        TypedQuery<Produto> query = entityManager.createQuery(cq);

        query.setFirstResult((int) pageable.getOffset()); // OFFSET
        query.setMaxResults(pageable.getPageSize());      // LIMIT

        List<Produto> content = query.getResultList();

        // 5. Retorna o objeto Page (Lista + Paginação + Total)
        return new PageImpl<>(content, pageable, totalElements);
    }

    // --------------------------------------------------------------------------
    // NOVO MÉTODO: Cria e executa a Criteria Query de CONTAGEM
    // --------------------------------------------------------------------------
    private Long countProdutos(ProdutoSearch produtoSearch) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // **Criação da Criteria Query para o tipo Long (COUNT)**
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Produto> produto = cq.from(Produto.class);

        // Define a função COUNT como a seleção
        cq.select(cb.count(produto));

        // Aplica os MESMOS predicados (WHERE) da consulta principal
        List<Predicate> predicates = buildPredicates(cb, produto, produtoSearch);
        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }

        // Executa a query de contagem
        return entityManager.createQuery(cq).getSingleResult();
    }

    // --------------------------------------------------------------------------
    // MÉTODOS AUXILIARES: Reutilizados para Contagem e Conteúdo
    // --------------------------------------------------------------------------

    /**
     * Constrói os predicados (filtros WHERE) com base no ProdutoSearch.
     * @param cb CriteriaBuilder
     * @param produto Root (FROM Produto p)
     * @param produtoSearch Critérios de busca
     * @return Lista de Predicates
     */
    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<Produto> produto, ProdutoSearch produtoSearch) {
        List<Predicate> predicates = new ArrayList<>();

        // Filtro: Nome (busca parcial, case-insensitive)
        if (produtoSearch.getNome() != null && !produtoSearch.getNome().trim().isEmpty()) {
            Predicate nomePredicate = cb.like(
                    cb.upper(produto.get("nome")),
                    "%" + produtoSearch.getNome().toUpperCase().trim() + "%"
            );
            predicates.add(nomePredicate);
        }

        // Filtro: Categoria (busca exata)
        if (produtoSearch.getCategoria() != null) {
            Predicate categoriaPredicate = cb.equal(produto.get("categoria"), produtoSearch.getCategoria());
            predicates.add(categoriaPredicate);
        }

        // Filtro: Quantidade em estoque (igualdade exata)
        if (produtoSearch.getQuantidadeEstoque() != null) {
            Predicate quantidadePredicate = cb.equal(produto.get("quantidadeEstoque"), produtoSearch.getQuantidadeEstoque());
            predicates.add(quantidadePredicate);
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

        return predicates;
    }

    /**
     * Aplica a ordenação (ORDER BY) na CriteriaQuery.
     */
    private void applyOrder(CriteriaBuilder cb, CriteriaQuery<Produto> cq, Root<Produto> produto, ProdutoSearch produtoSearch) {
        String sortBy = produtoSearch.getSortBy();
        SortOrder sortOrder = produtoSearch.getSortOrder() != null ? produtoSearch.getSortOrder() : SortOrder.ASC;

        if (sortBy != null && !sortBy.trim().isEmpty()) {
            String safeSortBy = getSafeSortField(sortBy);

            Order order = (sortOrder == SortOrder.DESC)
                    ? cb.desc(produto.get(safeSortBy))
                    : cb.asc(produto.get(safeSortBy));

            cq.orderBy(order);
        } else {
            // Ordenação padrão por ID
            cq.orderBy(cb.asc(produto.get("id")));
        }
    }

    // Mapeia campos permitidos para ordenação (segurança)
    private String getSafeSortField(String sortBy) {
        switch (sortBy.toLowerCase()) {
            case "nome":
                return "nome";
            case "categoria":
                return "categoria";
            case "quantidadeestoque":
                return "quantidadeEstoque";
            case "unidademedida":
                return "unidadeMedida";
            case "validade":
                return "validade";
            case "id":
            default:
                return "id";
        }
    }
}