package com.fiec.projeto_back_gastroflow.features.products.services.impl;


import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoDTO;
import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import com.fiec.projeto_back_gastroflow.features.products.repositories.ProdutoRepository;
import com.fiec.projeto_back_gastroflow.features.products.services.ProdutoService;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;


    // Criar Produto

    public void createProduto(ProdutoDTO produtoDTO, User userDetails) {
        log.info("createProduto - parâmetro: {}, by {}", produtoDTO, userDetails.getId());
        Produto produto = Produto.builder()
                .categoria(produtoDTO.getCategoria())
                .nome(produtoDTO.getNome())

                .build();
        produto.setCreatedBy(userDetails);


        produtoRepository.save(produto);
    }

    // Buscar Produto por ID
    public ProdutoDTO getById(Long id) {
        return produtoRepository.findById(id).map(produto ->
                new ProdutoDTO(
                        produto.getNome(),
                        produto.getCategoria(),
                        produto.getQuantidadeEstoque(),
                        produto.getUnidadeMedida(),
                        produto.getValidade()
                )
        ).orElse(null);
    }

    public List<ProdutoDTO> getAllByNome(String nome) {
        return produtoRepository.findAllByNome(nome).stream()
                .map(produto -> new ProdutoDTO(
                        produto.getNome(),
                        produto.getCategoria(),
                        produto.getQuantidadeEstoque(),
                        produto.getUnidadeMedida(),
                        produto.getValidade()
                ))
                .toList();
    }

    // Listar todos os produtos
    public List<ProdutoDTO> findAll() {
        return produtoRepository.findAll().stream()
                .map(produto -> new ProdutoDTO(
                        produto.getNome(),
                        produto.getCategoria(),
                        produto.getQuantidadeEstoque(),
                        produto.getUnidadeMedida(),
                        produto.getValidade()
                ))
                .toList();
    }

    // Atualizar Produto por ID
    public boolean updateProdutoById(Long id, ProdutoDTO produtoDTO) {
        return produtoRepository.findById(id).map(produto -> {
            produto.setNome(produtoDTO.getNome());
            produto.setCategoria(produtoDTO.getCategoria());
            produto.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());
            produto.setUnidadeMedida(produtoDTO.getUnidadeMedida());
            produto.setValidade(produtoDTO.getValidade());

            produtoRepository.save(produto);
            return true;
        }).orElse(false);
    }

    // Deletar Produto por ID
    public void deleteProdutoById(Long id) {
        produtoRepository.deleteById(id);
    }
}

