package com.fiec.projeto_back_gastroflow.features.entrada.services.impl;

import com.fiec.projeto_back_gastroflow.features.entrada.dto.EntradaDTO;
import com.fiec.projeto_back_gastroflow.features.entrada.models.Entrada;
import com.fiec.projeto_back_gastroflow.features.entrada.repositories.EntradaRepository;
import com.fiec.projeto_back_gastroflow.features.entrada.services.EntradaService;
import com.fiec.projeto_back_gastroflow.features.products.repositories.ProdutoRepository; // ID Long
import com.fiec.projeto_back_gastroflow.features.supplier.models.Supplier;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import com.fiec.projeto_back_gastroflow.features.supplier.repositories.SupplierRepository;
import com.fiec.projeto_back_gastroflow.features.user.repositories.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EntradaServiceImpl implements EntradaService {

    private final EntradaRepository entradaRepository;
    private final ProdutoRepository produtoRepository;
    // Repositórios simulados para resolver as chaves estrangeiras UUID
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;

    // --- Mappers & Helpers ---

    private EntradaDTO toDTO(Entrada entrada) {
        // Converte Model para DTO, extraindo IDs de chave estrangeira
        return new EntradaDTO(
                entrada.getDataEntrada(),
                entrada.getQuantidade(),
                entrada.getObservacao(),
                entrada.getProduto().getId(),
                entrada.getFornecedor() != null ? entrada.getFornecedor().getId() : null,
                entrada.getUser().getId(),
                entrada.getId()
        );
    }

    // Método auxiliar para buscar entidades por ID e lançar exceção (simulado)
    private User findUserById(UUID id) {
        // Simulação de busca
        return userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com ID: " + id));
    }

    private Supplier findSupplierById(UUID id) {
        // Simulação de busca
        return supplierRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado com ID: " + id));
    }


    // --- Métodos CRUD ---

    @Override
    public void createEntrada(EntradaDTO entradaDTO) {
        // Busca de entidades por ID para criar o objeto Entrada
        var produto = produtoRepository.findById(entradaDTO.getProdutoId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com ID: " + entradaDTO.getProdutoId()));

        var user = findUserById(entradaDTO.getUserId());
        // Fornecedor é opcional (nullable = true no Model)
        var supplier = entradaDTO.getFornecedorId() != null ?
                findSupplierById(entradaDTO.getFornecedorId()) :
                null;

        Entrada entrada = new Entrada(
                entradaDTO.getDataEntrada(),
                entradaDTO.getQuantidade(),
                entradaDTO.getObservacao(),
                produto,
                supplier,
                user
        );

        entradaRepository.save(entrada);
    }

    // Busca por ID (UUID) - Corrigido para usar UUID
    @Override
    public EntradaDTO getById(Long id) {
        return entradaRepository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrada não encontrada com ID: " + id));
    }

    // Listar todas as Entradas por ID do Produto (ID Long)
    @Override
    public List<EntradaDTO> getAllByProdutoId(Long produtoId) {
        return entradaRepository.findAllByProdutoId(produtoId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Listar todas as Entradas
    @Override
    public List<EntradaDTO> findAll() {
        return entradaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Atualizar Entrada por ID (UUID) - Corrigido para usar UUID
    @Override
    public boolean updateEntradaById(Long id, EntradaDTO entradaDTO) {
        return entradaRepository.findById(id).map(entrada -> {

            // Re-busca das entidades para update
            var produto = produtoRepository.findById(entradaDTO.getProdutoId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com ID: " + entradaDTO.getProdutoId()));

            var user = findUserById(entradaDTO.getUserId());
            var supplier = entradaDTO.getFornecedorId() != null ?
                    findSupplierById(entradaDTO.getFornecedorId()) :
                    null;

            // Atualiza os campos
            entrada.setDataEntrada(entradaDTO.getDataEntrada());
            entrada.setQuantidade(entradaDTO.getQuantidade());
            entrada.setObservacao(entradaDTO.getObservacao());
            entrada.setProduto(produto);
            entrada.setFornecedor(supplier);
            entrada.setUser(user);

            entradaRepository.save(entrada);
            return true;
        }).orElse(false);
    }

    // Deletar Entrada por ID (UUID) - Corrigido para usar UUID
    @Override
    public void deleteEntradaById(Long id) {
        entradaRepository.deleteById(id);
    }
}