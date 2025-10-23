package com.fiec.projeto_back_gastroflow.features.receita.services.impl;

import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import com.fiec.projeto_back_gastroflow.features.products.repositories.ProdutoRepository;
import com.fiec.projeto_back_gastroflow.features.receita.dto.ReceitaDTO;
import com.fiec.projeto_back_gastroflow.features.receita.models.Receita;
import com.fiec.projeto_back_gastroflow.features.receita.repositories.ReceitaRepository;
import com.fiec.projeto_back_gastroflow.features.receita.services.ReceitaService;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import com.fiec.projeto_back_gastroflow.features.user.repositories.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReceitaServiceImpl implements ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final ProdutoRepository produtoRepository;
    private final UserRepository userRepository;

    @Override
    public void createReceita(ReceitaDTO receitaDTO, java.util.UUID usuarioId) {
        Receita receita = new Receita();
        receita.setNome(receitaDTO.getNome());
        receita.setDescricao(receitaDTO.getDescricao());
        receita.setTempoPreparo(receitaDTO.getTempoPreparo());
        receita.setRendimento(receitaDTO.getRendimento());
        receita.setTipo(receitaDTO.getTipo());
        receita.setUsuarioAlteracao(receitaDTO.getUsuarioAlteracao());
        receita.setProfessorReceita(receitaDTO.getProfessorReceita());

        // Relacionar usuário
        User user = userRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Erro de Autenticação: Usuário logado não encontrado."));
        receita.setUser(user);

        // Relacionar produtos
        if (receitaDTO.getProdutoIds() != null) {
            List<Produto> produtos = produtoRepository.findAllById(receitaDTO.getProdutoIds());
            receita.setProdutos(produtos);
        }

        receitaRepository.save(receita);
    }

    @Override
    public ReceitaDTO getById(Long id) {
        return receitaRepository.findById(id).map(receita -> {
            ReceitaDTO dto = new ReceitaDTO();
            dto.setNome(receita.getNome());
            dto.setDescricao(receita.getDescricao());
            dto.setTempoPreparo(receita.getTempoPreparo());
            dto.setRendimento(receita.getRendimento());
            dto.setTipo(receita.getTipo());
            dto.setDataAlteracao(receita.getDataAlteracao());
            dto.setUsuarioAlteracao(receita.getUsuarioAlteracao());
            dto.setDataCadastro(receita.getDataCadastro());
            dto.setProfessorReceita(receita.getProfessorReceita());

            if (receita.getUser() != null) {
                dto.setUserId(receita.getUser().getId());
            }

            dto.setProdutoIds(
                    receita.getProdutos().stream().map(Produto::getId).toList()
            );
            return dto;
        }).orElse(null);
    }

    @Override
    public List<ReceitaDTO> findAll() {
        return receitaRepository.findAll().stream().map(receita -> {
            ReceitaDTO dto = new ReceitaDTO();
            dto.setNome(receita.getNome());
            dto.setDescricao(receita.getDescricao());
            dto.setTempoPreparo(receita.getTempoPreparo());
            dto.setRendimento(receita.getRendimento());
            dto.setTipo(receita.getTipo());
            dto.setDataAlteracao(receita.getDataAlteracao());
            dto.setUsuarioAlteracao(receita.getUsuarioAlteracao());
            dto.setDataCadastro(receita.getDataCadastro());
            dto.setProfessorReceita(receita.getProfessorReceita());

            if (receita.getUser() != null) {
                dto.setUserId(receita.getUser().getId());
            }

            dto.setProdutoIds(
                    receita.getProdutos().stream().map(Produto::getId).toList()
            );
            return dto;
        }).toList();
    }

    @Override
    public boolean updateReceitaById(Long id, ReceitaDTO receitaDTO) {
        return receitaRepository.findById(id).map(receita -> {
            receita.setNome(receitaDTO.getNome());
            receita.setDescricao(receitaDTO.getDescricao());
            receita.setTempoPreparo(receitaDTO.getTempoPreparo());
            receita.setRendimento(receitaDTO.getRendimento());
            receita.setTipo(receitaDTO.getTipo());
            receita.setUsuarioAlteracao(receitaDTO.getUsuarioAlteracao());
            receita.setProfessorReceita(receitaDTO.getProfessorReceita());

            // Atualizar usuário
            if (receitaDTO.getUserId() != null) {
                User user = userRepository.findById(receitaDTO.getUserId())
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
                receita.setUser(user);
            }

            // Atualizar produtos
            if (receitaDTO.getProdutoIds() != null) {
                List<Produto> produtos = produtoRepository.findAllById(receitaDTO.getProdutoIds());
                receita.setProdutos(produtos);
            }

            receitaRepository.save(receita);
            return true;
        }).orElse(false);
    }

    @Override
    public void deleteReceitaById(Long id) {
        receitaRepository.deleteById(id);
    }
}
