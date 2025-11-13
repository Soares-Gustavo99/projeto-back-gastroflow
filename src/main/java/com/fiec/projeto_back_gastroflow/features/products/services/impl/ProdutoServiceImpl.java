package com.fiec.projeto_back_gastroflow.features.products.services.impl;


import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoDTO;
import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoSearch;
import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import com.fiec.projeto_back_gastroflow.features.products.repositories.ProdutoRepository;
import com.fiec.projeto_back_gastroflow.features.products.services.ProdutoService;
import com.fiec.projeto_back_gastroflow.utils.ImageUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;


    // Criar Produto
    public void createProduto(ProdutoDTO produtoDTO) {
        Produto produto = new Produto(
                null,
                produtoDTO.getNome(),
                produtoDTO.getCategoria(),
                produtoDTO.getQuantidadeEstoque(),
                produtoDTO.getUnidadeMedida(),
                produtoDTO.getValidade(),
                null,
                null,
                null // a lista de receitas será setada pelo ReceitaService
        );

        produtoRepository.save(produto);
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
                        produto.getPicture()
                )
        ).orElse(null);
    }

    public Produto findProdutoById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Produto não encontrado com ID: " + id));
    }

    public void insertProdutoImage(Long id, MultipartFile image){

        // 1. Encontrar o produto (Modelo)
        Produto produto = findProdutoById(id);

        // 2. Salvar a imagem e obter o nome do arquivo
        String imageName = ImageUtils.saveImage(image); // Assumindo ImageUtils já foi copiado/implementado

        // 3. Associar e salvar o modelo
        produto.setPicture(imageName);
        produtoRepository.save(produto);
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
                        produto.getPicture()
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
                        produto.getPicture()
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
    public List<ProdutoDTO> findAllWithQueries(ProdutoSearch produtoSearch) {
        List<Produto> produtos = produtoRepository.findProdutos(produtoSearch);

        if (CollectionUtils.isEmpty(produtos)) {
            return List.of();
        } else {
            return produtos.stream()
                    .map(produto -> ProdutoDTO.builder()
                            .nome(produto.getNome())
                            .categoria(produto.getCategoria())
                            .quantidadeEstoque(produto.getQuantidadeEstoque())
                            .unidadeMedida(produto.getUnidadeMedida())
                            .validade(produto.getValidade())
                            .build()
                    )
                    .toList();
        }
    }

    // Deletar Produto por ID
    public void deleteProdutoById(Long id) {
        produtoRepository.deleteById(id);
    }
}

