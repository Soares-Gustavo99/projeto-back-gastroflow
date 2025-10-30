package com.fiec.projeto_back_gastroflow.features.retirada.services.impl;

import com.fiec.projeto_back_gastroflow.features.retirada.dto.RetiradaDTO;
import com.fiec.projeto_back_gastroflow.features.retirada.models.Retirada;
import com.fiec.projeto_back_gastroflow.features.retirada.repositories.RetiradaRepository;
import com.fiec.projeto_back_gastroflow.features.retirada.services.RetiradaService;
import com.fiec.projeto_back_gastroflow.features.products.repositories.ProdutoRepository;
import com.fiec.projeto_back_gastroflow.features.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RetiradaServiceImpl implements RetiradaService {

    private final RetiradaRepository retiradaRepository;
    private final ProdutoRepository produtoRepository;
    private final UserRepository userRepository; // Assumindo que você tem um UserRepository

    // Conversão de Model para DTO
    private RetiradaDTO mapModelToDto(Retirada model) {
        return new RetiradaDTO(
                model.getId(),
                model.getDataRetirada(),
                model.getQuantidade(),
                model.getObservacao(),
                model.getProduto().getId(),
                model.getUser().getId()
        );
    }

    // Conversão de DTO para Model (incluindo busca de entidades)
    private Retirada mapDtoToModel(RetiradaDTO dto) {
        var produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com ID: " + dto.getProdutoId()));

        var user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com ID: " + dto.getUserId()));

        return new Retirada(
                dto.getDataRetirada(),
                dto.getQuantidade(),
                dto.getObservacao(),
                produto,
                user
        );
    }

    @Override
    public void createRetirada(RetiradaDTO retiradaDTO) {
        // TODO: Adicionar lógica de verificação de estoque
        Retirada retirada = mapDtoToModel(retiradaDTO);
        retiradaRepository.save(retirada);
    }

    @Override
    public RetiradaDTO getById(Long id) {
        return retiradaRepository.findById(id)
                .map(this::mapModelToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Retirada não encontrada com ID: " + id));
    }

    @Override
    public List<RetiradaDTO> getAllByProdutoId(Long produtoId) {
        return retiradaRepository.findAllByProdutoId(produtoId).stream()
                .map(this::mapModelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RetiradaDTO> findAll() {
        return retiradaRepository.findAll().stream()
                .map(this::mapModelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateRetiradaById(Long id, RetiradaDTO retiradaDTO) {
        return retiradaRepository.findById(id).map(retirada -> {

            var produto = produtoRepository.findById(retiradaDTO.getProdutoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com ID: " + retiradaDTO.getProdutoId()));

            var user = userRepository.findById(retiradaDTO.getUserId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com ID: " + retiradaDTO.getUserId()));

            retirada.setDataRetirada(retiradaDTO.getDataRetirada());
            retirada.setQuantidade(retiradaDTO.getQuantidade());
            retirada.setObservacao(retiradaDTO.getObservacao());
            retirada.setProduto(produto);
            retirada.setUser(user);

            retiradaRepository.save(retirada);
            return true;
        }).orElse(false);
    }

    @Override
    public void deleteRetiradaById(Long id) {
        retiradaRepository.deleteById(id);
    }
}