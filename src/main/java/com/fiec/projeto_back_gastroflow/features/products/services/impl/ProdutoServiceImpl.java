package com.fiec.projeto_back_gastroflow.features.products.services.impl;


import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoDTO;
import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoDetailsDTO;
import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoSearch;
import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoSummaryDTO;
import com.fiec.projeto_back_gastroflow.features.products.models.Categoria;
import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import com.fiec.projeto_back_gastroflow.features.products.models.UnidadeMedida;
import com.fiec.projeto_back_gastroflow.features.products.repositories.ProdutoDetailsRepository;
import com.fiec.projeto_back_gastroflow.features.products.repositories.ProdutoRepository;
import com.fiec.projeto_back_gastroflow.features.products.services.ProdutoService;
import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoDetailsDTO.ProdutoLista;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    private final ProdutoDetailsRepository produtoDetailsRepository;


    // Criar Produto
    public void createProduto(ProdutoDTO produtoDTO) {
        Produto produto = new Produto(
                null,
                produtoDTO.getNome(),
                produtoDTO.getCategoria(),
                produtoDTO.getQuantidadeEstoque(),
                produtoDTO.getUnidadeMedida(),
                produtoDTO.getValidade(),
                produtoDTO.getImagem(),
                produtoDTO.getEntrada(),
                null // a lista de receitas será setada pelo ReceitaService
        );

        produtoRepository.save(produto);
    }

    public ProdutoDetailsDTO findByName(String nome) {
        // 1. Buscar todos os produtos que contenham o nome, ignorando caixa.
        List<Produto> produtos = produtoDetailsRepository.findAllByNomeContainingIgnoreCase(nome);

        if (produtos.isEmpty()) {
            throw new RuntimeException("Produto não encontrado com o nome: " + nome); // Mudar para uma Exception apropriada (e.g., NotFoundException)
        }

        // 2. Agrupar informações do produto e calcular a contagem total de estoque.
        String nomeProduto = produtos.get(0).getNome();
        UnidadeMedida unidadeMedida = produtos.get(0).getUnidadeMedida();
        Categoria categoria = produtos.get(0).getCategoria();
        String imagem = produtos.get(0).getImagem();

        // Mapear cada item (Produto) encontrado para um ProdutoLista
        List<ProdutoLista> listaItens = produtos.stream()
                .map(produto -> ProdutoLista.builder() // Aqui você usa o nome simples
                        .id(produto.getId())
                        .quantidadeEstoque(produto.getQuantidadeEstoque())
                        .validade(produto.getValidade())
                        .entradaId(produto.getEntrada() != null ? produto.getEntrada().getId() : null)
                        .build())
                .toList();;

        // 3. Calcular a contagem total de estoque (soma das quantidadesEstoque).
        Integer contagemTotal = produtos.stream()
                .mapToInt(Produto::getQuantidadeEstoque)
                .sum();

        // 4. Construir e retornar o DTO de Detalhes.
        return ProdutoDetailsDTO.builder()
                .nome(nomeProduto)
                .unidadeMedida(unidadeMedida)
                .categoria(categoria)
                .imagem(imagem)
                .contagem(contagemTotal) // Total de estoque
                .produtos(listaItens) // Lista de itens específicos
                .build();
    }

    // Buscar Produto por ID
    public ProdutoDTO getById(Long id) {
        return produtoRepository.findById(id).map(produto ->
                new ProdutoDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getCategoria(),
                        produto.getQuantidadeEstoque(),
                        produto.getUnidadeMedida(),
                        produto.getValidade(),
                        produto.getImagem(),
                        produto.getEntrada()
                )
        ).orElse(null);
    }



    public List<ProdutoDTO> getAllByNome(String nome) {
        return produtoRepository.findAllByNome(nome).stream()
                .map(produto -> new ProdutoDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getCategoria(),
                        produto.getQuantidadeEstoque(),
                        produto.getUnidadeMedida(),
                        produto.getValidade(),
                        produto.getImagem(),
                        produto.getEntrada()
                ))
                .toList();
    }

    // Listar todos os produtos
    public List<ProdutoDTO> findAll() {
        return produtoRepository.findAll().stream()
                .map(produto -> new ProdutoDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getCategoria(),
                        produto.getQuantidadeEstoque(),
                        produto.getUnidadeMedida(),
                        produto.getValidade(),
                        produto.getImagem(),
                        produto.getEntrada()
                ))
                .toList();
    }

    // Atualizar Produto por ID
    public boolean updateProdutoById(Long id, ProdutoDTO produtoDTO) {
        return produtoRepository.findById(id).map(produto -> {
            if (produtoDTO.getNome() != null) produto.setNome(produtoDTO.getNome());
            if (produtoDTO.getCategoria() != null) produto.setCategoria(produtoDTO.getCategoria());
            if (produtoDTO.getQuantidadeEstoque() != null) produto.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());
            if (produtoDTO.getUnidadeMedida() != null) produto.setUnidadeMedida(produtoDTO.getUnidadeMedida());
            if (produtoDTO.getValidade() != null) produto.setValidade(produtoDTO.getValidade());

            produtoRepository.save(produto);
            return true;
        }).orElse(false);
    }

    @Override
    public List<ProdutoSummaryDTO> findAllWithQueries(ProdutoSearch produtoSearch) {
        List<ProdutoSummaryDTO> produtos = produtoRepository.findProdutos(produtoSearch);

        if (CollectionUtils.isEmpty(produtos)) {
            return List.of();
        } else {
            return produtos;
        }
    }

    // Deletar Produto por ID
    public void deleteProdutoById(Long id) {
        produtoRepository.deleteById(id);
    }
}

