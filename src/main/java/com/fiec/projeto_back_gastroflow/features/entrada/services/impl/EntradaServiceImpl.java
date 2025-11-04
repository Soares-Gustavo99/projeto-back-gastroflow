package com.fiec.projeto_back_gastroflow.features.entrada.services.impl;

import com.fiec.projeto_back_gastroflow.features.entrada.dto.EntradaDTO;
import com.fiec.projeto_back_gastroflow.features.entrada.models.Entrada;
import com.fiec.projeto_back_gastroflow.features.entrada.repositories.EntradaRepository;
import com.fiec.projeto_back_gastroflow.features.entrada.services.EntradaService;
import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import com.fiec.projeto_back_gastroflow.features.products.repositories.ProdutoRepository;
import com.fiec.projeto_back_gastroflow.features.supplier.models.Supplier;
import com.fiec.projeto_back_gastroflow.features.supplier.repositories.SupplierRepository;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
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
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;

    // Métodos utilitários
    private User findUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com ID: " + id));
    }

    private Supplier findSupplierById(Long id) {
        return supplierRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado com ID: " + id));
    }

    // Mapeamento Model para DTO (Atualizado para incluir novos campos de auditoria)
    private EntradaDTO toDTO(Entrada model) {
        EntradaDTO dto = new EntradaDTO();
        dto.setId(model.getId());
        dto.setDataEntrada(model.getDataEntrada());
        dto.setQuantidade(model.getQuantidade());
        dto.setObservacao(model.getObservacao());

        // Campos de auditoria
        dto.setDataCadastro(model.getDataCadastro());
        dto.setDataAlteracao(model.getDataAlteracao());

        // Chaves Estrangeiras
        if (model.getProduto() != null) {
            dto.setProdutoId(model.getProduto().getId());
        }
        if (model.getFornecedor() != null) {
            dto.setFornecedorId(model.getFornecedor().getId());
        }
        if (model.getUser() != null) {
            dto.setUserId(model.getUser().getId());
        }
        return dto;
    }

    // Criar Entrada - Recebe usuarioID
    @Override
    public void createEntrada(EntradaDTO entradaDTO, UUID usuarioID) {
        // Busca as entidades relacionadas
        var produto = produtoRepository.findById(entradaDTO.getProdutoId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com ID: " + entradaDTO.getProdutoId()));

        var user = findUserById(usuarioID); // Usa o ID do usuário logado como usuário de cadastro
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

    // Buscar Entrada por ID (Long)
    @Override
    public EntradaDTO getById(Long id) {
        return entradaRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrada não encontrada com ID: " + id));
    }

    // Buscar Entradas por ID do Produto (Long)
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

    // Atualizar Entrada por ID (Long) - Recebe usuarioID
    @Override
    public boolean updateEntradaById(Long id, EntradaDTO entradaDTO, UUID usuarioId) {
        return entradaRepository.findById(id).map(entrada -> {

            // Re-busca das entidades para update
            var produto = produtoRepository.findById(entradaDTO.getProdutoId()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com ID: " + entradaDTO.getProdutoId()));

            // O userId no DTO pode ser o usuário original do cadastro ou pode ser atualizado.
            // Aqui, mantemos a referência, buscando o User.
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

    // Deletar Entrada por ID (Long)
    @Override
    public void deleteEntradaById(Long id) {
        if (!entradaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrada não encontrada com ID: " + id);
        }
        entradaRepository.deleteById(id);
    }


}