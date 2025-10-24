package com.fiec.projeto_back_gastroflow.features.products.repositories.impl;

import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoSearch;
import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import com.fiec.projeto_back_gastroflow.features.products.models.SortOrder;
import com.fiec.projeto_back_gastroflow.features.products.repositories.ProdutoCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
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
     * Busca produtos de forma customizada usando a Criteria API do JPA.
     * @param produtoSearch Objeto com os critérios de busca e ordenação.
     * @return Lista de produtos que atendem aos critérios.
     */
    public List<Produto> findProdutos(ProdutoSearch produtoSearch) {

        // 1. Inicialização da Criteria API
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> cq = cb.createQuery(Produto.class);
        Root<Produto> produto = cq.from(Produto.class); // FROM Produto p

        List<Predicate> predicates = new ArrayList<>();

        // 2. Construção dinâmica dos filtros (WHERE)

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

        // Filtro: Validade (igualdade exata, pode adaptar para < ou > se quiser)
        if (produtoSearch.getValidade() != null) {
            Predicate validadePredicate = cb.equal(produto.get("validade"), produtoSearch.getValidade());
            predicates.add(validadePredicate);
        }

        // Combina todos os filtros com AND
        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        // 3. Ordenação dinâmica (ORDER BY)
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

        // 4. Execução da query
        TypedQuery<Produto> query = entityManager.createQuery(cq);
        return query.getResultList();
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
