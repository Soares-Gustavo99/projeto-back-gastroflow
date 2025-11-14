package com.fiec.projeto_back_gastroflow.features.aula.services.impl;

import com.fiec.projeto_back_gastroflow.exceptions.EstoqueInsuficienteException;
import com.fiec.projeto_back_gastroflow.features.aula.dto.AulaDTO;
import com.fiec.projeto_back_gastroflow.features.aula.models.Aula;
import com.fiec.projeto_back_gastroflow.features.aula.repositories.AulaRepository;
import com.fiec.projeto_back_gastroflow.features.aula.services.AulaService;
import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import com.fiec.projeto_back_gastroflow.features.products.repositories.ProdutoRepository;
import com.fiec.projeto_back_gastroflow.features.receita.models.Receita;
import com.fiec.projeto_back_gastroflow.features.receita.repositories.ReceitaRepository;
import com.fiec.projeto_back_gastroflow.features.receitaProduto.ReceitaProduto;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import com.fiec.projeto_back_gastroflow.features.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AulaServiceImpl implements AulaService {

    private final AulaRepository aulaRepository;
    private final ReceitaRepository receitaRepository; // Para N:N com Receita
    private final UserRepository userRepository; // Para ManyToOne com User
    private final ProdutoRepository produtoRepository;

    @Transactional
    @Override
    public void createAula(AulaDTO aulaDTO, UUID usuarioId) {
        // Passo 1: Obter as receitas da Aula
        List<Receita> receitas = null;
        if (aulaDTO.getReceitaIds() != null && !aulaDTO.getReceitaIds().isEmpty()) {
            receitas = receitaRepository.findAllById(aulaDTO.getReceitaIds());
        }

        // Passo 2: Consolidar a Quantidade Total Requerida por Produto
        Map<Long, Integer> produtosNecessarios = new HashMap<>();

        if (receitas != null) {
            for (Receita receita : receitas) {
                for (ReceitaProduto item : receita.getProdutos()) {
                    Long produtoId = item.getProduto().getId();
                    Integer quantidadeRequeridaNaReceita = item.getQuantidade();

                    produtosNecessarios.merge(
                            produtoId,
                            quantidadeRequeridaNaReceita,
                            Integer::sum
                    );
                }
            }
        }

        // Passo 3: Verificar Estoque e Realizar a Baixa
        for (Map.Entry<Long, Integer> entry : produtosNecessarios.entrySet()) {
            Long produtoId = entry.getKey();
            Integer quantidadeRequerida = entry.getValue();

            // Buscar o produto.
            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new RuntimeException("Produto (ID: " + produtoId + ") não encontrado no sistema."));

            // Checagem de Estoque
            if (produto.getQuantidadeEstoque() < quantidadeRequerida) {
                // Lançar exceção para forçar o ROLLBACK de TUDO (incluindo a aula e as receitas).
                throw new EstoqueInsuficienteException(
                        "Estoque insuficiente para o produto: " + produto.getNome() +
                                ". Estoque atual: " + produto.getQuantidadeEstoque() +
                                ", Requerido para a aula: " + quantidadeRequerida
                );
            }

            // **AÇÃO OBRIGATÓRIA: ATUALIZAÇÃO/BAIXA DE ESTOQUE**
            // O produto é uma entidade gerenciada. A alteração será persistida no COMMIT.
            Integer novoEstoque = produto.getQuantidadeEstoque() - quantidadeRequerida;
            produto.setQuantidadeEstoque(novoEstoque);

            // Chamamos save explicitamente para garantir que a alteração seja marcada para persistência.
            // Em muitos casos, se a entidade for gerenciada, a chamada a save é opcional,
            // mas é uma boa prática para clareza em transações.
            produtoRepository.save(produto);
        }


        // Passo 4: Persistência da Aula (Só ocorre se o estoque foi suficiente e a baixa foi registrada)
        Aula aula = new Aula();
        aula.setNome(aulaDTO.getNome());
        aula.setDescricao(aulaDTO.getDescricao());
        aula.setData(aulaDTO.getData());
        aula.setInstrutor(aulaDTO.getInstrutor());
        aula.setMateria(aulaDTO.getMateria());
        aula.setAno(aulaDTO.getAno());
        aula.setSemestre(aulaDTO.getSemestre());
        aula.setModulo(aulaDTO.getModulo());
        aula.setPeriodo(aulaDTO.getPeriodo());

        // Relacionar usuário (fk_usuario_id)
        User user = userRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Erro de Autenticação: Usuário logado não encontrado."));
        aula.setUser(user);

        // Relacionar receitas (N:N)
        if (receitas != null) {
            aula.setReceitas(receitas);
        }

        aulaRepository.save(aula);

        // FIM DA TRANSAÇÃO: Se chegou aqui, a Aula é salva, e o estoque dos Produtos é atualizado (COMMIT).
        // Se ocorrer uma exceção (mesmo que após o save do Produto), ambas as operações serão revertidas (ROLLBACK).
    }

    @Override
    public AulaDTO getById(Long id) {
        return aulaRepository.findById(id).map(aula -> {
            AulaDTO dto = new AulaDTO();
            dto.setId(aula.getId());
            dto.setDescricao(aula.getDescricao());
            dto.setData(aula.getData());
            dto.setInstrutor(aula.getInstrutor());
            dto.setMateria(aula.getMateria());
            dto.setAno(aula.getAno());
            dto.setSemestre(aula.getSemestre());
            dto.setModulo(aula.getModulo());
            dto.setPeriodo(aula.getPeriodo());

            // Mapear Receita IDs
            dto.setReceitaIds(
                    aula.getReceitas().stream().map(Receita::getId).toList()
            );

             if (aula.getUser() != null) {
                 dto.setUserId(aula.getUser().getId());
             }

            return dto;
        }).orElse(null);
    }

    @Override
    public List<AulaDTO> findAll() {
        return aulaRepository.findAll().stream().map(aula -> {
            AulaDTO dto = new AulaDTO();
            dto.setId(aula.getId());
            dto.setDescricao(aula.getDescricao());
            dto.setData(aula.getData());
            dto.setInstrutor(aula.getInstrutor());
            dto.setMateria(aula.getMateria());
            dto.setAno(aula.getAno());
            dto.setSemestre(aula.getSemestre());
            dto.setModulo(aula.getModulo());
            dto.setPeriodo(aula.getPeriodo());

            // Mapear Receita IDs
            dto.setReceitaIds(
                    aula.getReceitas().stream().map(Receita::getId).toList()
            );

            // Não incluí o userId no DTO, mas se fosse necessário seria assim:
            // if (aula.getUser() != null) {
            //     dto.setUserId(aula.getUser().getId());
            // }

            return dto;
        }).toList();
    }

    @Override
    public boolean updateAulaById(Long id, AulaDTO aulaDTO, UUID usuarioId) {
        return aulaRepository.findById(id).map(aula -> {
            aula.setDescricao(aulaDTO.getDescricao());
            aula.setData(aulaDTO.getData());
            aula.setInstrutor(aulaDTO.getInstrutor());
            aula.setMateria(aulaDTO.getMateria());
            aula.setAno(aulaDTO.getAno());
            aula.setSemestre(aulaDTO.getSemestre());
            aula.setModulo(aulaDTO.getModulo());
            aula.setPeriodo(aulaDTO.getPeriodo());

            // Atualizar receitas (N:N)
            if (aulaDTO.getReceitaIds() != null) {
                List<Receita> receitas = receitaRepository.findAllById(aulaDTO.getReceitaIds());
                aula.setReceitas(receitas);
            }

            // Não há campos de data/usuário de alteração automática no modelo Aula fornecido,
            // mas manterei a estrutura do `ReceitaServiceImpl` para referência futura, se necessário.

            // Exemplo de atualização de usuário/data de alteração, se existissem no modelo Aula:
            // aula.setUsuarioAlteracao(usuarioId.toString());
            // (Se o modelo Aula tivesse @UpdateTimestamp, ele seria atualizado automaticamente)


            aulaRepository.save(aula);
            return true;
        }).orElse(false);
    }

    @Override
    public void deleteAulaById(Long id) {
        aulaRepository.deleteById(id);
    }
}