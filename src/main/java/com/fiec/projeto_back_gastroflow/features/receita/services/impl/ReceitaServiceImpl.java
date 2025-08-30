package com.fiec.projeto_back_gastroflow.features.receita.services.impl;


//COLOCAR OS IMPORTS DOS PRODUTOS ASSIM QUE MIGRAR ELES DO GASTROFLOWBE
import com.fiec.projeto_back_gastroflow.features.receita.dto.ReceitaDTO;
import com.fiec.projeto_back_gastroflow.features.produto.models.Produto;
import com.fiec.projeto_back_gastroflow.features.receita.models.Receita;
import com.fiec.projeto_back_gastroflow.features.produto.repositories.ProdutoRepository;
import com.fiec.projeto_back_gastroflow.features.receita.repositories.ReceitaRepository;
import com.fiec.projeto_back_gastroflow.features.receita.services.ReceitaService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReceitaServiceImpl implements ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final ProdutoRepository produtoRepository;

    @Override
    public void createReceita(ReceitaDTO receitaDTO) {
        Receita receita = new Receita();
        receita.setNome(receitaDTO.getNome());
        receita.setDescricao(receitaDTO.getDescricao());
        receita.setTempoPreparo(receitaDTO.getTempoPreparo());
        receita.setTextoPreparo(receitaDTO.getTextoPreparo());

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
            dto.setId(receita.getId());
            dto.setNome(receita.getNome());
            dto.setDescricao(receita.getDescricao());
            dto.setTempoPreparo(receita.getTempoPreparo());
            dto.setTextoPreparo(receita.getTextoPreparo());
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
            dto.setId(receita.getId());
            dto.setNome(receita.getNome());
            dto.setDescricao(receita.getDescricao());
            dto.setTempoPreparo(receita.getTempoPreparo());
            dto.setTextoPreparo(receita.getTextoPreparo());
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
            receita.setTextoPreparo(receitaDTO.getTextoPreparo());

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
