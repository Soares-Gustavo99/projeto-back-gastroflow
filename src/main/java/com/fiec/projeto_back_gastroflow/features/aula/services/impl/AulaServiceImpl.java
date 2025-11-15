package com.fiec.projeto_back_gastroflow.features.aula.services.impl;

import com.fiec.projeto_back_gastroflow.exceptions.EstoqueInsuficienteException;
import com.fiec.projeto_back_gastroflow.features.aula.dto.AulaDTO;
import com.fiec.projeto_back_gastroflow.features.aula.models.Aula;
import com.fiec.projeto_back_gastroflow.features.aula.repositories.AulaRepository;
import com.fiec.projeto_back_gastroflow.features.aula.services.AulaService;
import com.fiec.projeto_back_gastroflow.features.aulaReceitas.AulaReceita;
import com.fiec.projeto_back_gastroflow.features.aulaReceitas.AulaReceitaItemDTO;
import com.fiec.projeto_back_gastroflow.features.aulaReceitas.repositories.AulaReceitaRepository;
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
import com.fiec.projeto_back_gastroflow.exceptions.ResourceNotFoundException;

import java.util.*;

@Service
@AllArgsConstructor
public class AulaServiceImpl implements AulaService {

    private final AulaRepository aulaRepository;
    private final ReceitaRepository receitaRepository; // Para N:N com Receita
    private final UserRepository userRepository; // Para ManyToOne com User
    private final ProdutoRepository produtoRepository;
    private final AulaReceitaRepository aulaReceitaRepository;

    @Transactional
    public Aula createAula(AulaDTO aulaDTO , UUID usuarioId) {

        // 1. Mapeamento Manual do DTO para a Entidade Aula (usando @Builder)

        // 1a. Busca o Usuário (necessário para a relação ManyToOne)
        User user = userRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        // 1b. Cria a entidade Aula usando o Builder
        Aula aula = Aula.builder()
                .nome(aulaDTO.getNome())
                .descricao(aulaDTO.getDescricao())
                .data(aulaDTO.getData())
                .instrutor(aulaDTO.getInstrutor())
                .materia(aulaDTO.getMateria())
                .ano(aulaDTO.getAno())
                .semestre(aulaDTO.getSemestre())
                .modulo(aulaDTO.getModulo())
                .periodo(aulaDTO.getPeriodo())
                .user(user) // Define o usuário
                .receitas(new ArrayList<>()) // Inicializa a lista de junção (será preenchida no loop)
                .build();

        aulaRepository.save(aula); // Persiste a Aula base para obter o ID

        // Estrutura para rastrear o total de produtos necessários (para o transactional)
        Map<Long, Integer> produtosParaRetirar = new HashMap<>();

        // 2. Itera sobre as Receitas do DTO e calcula o estoque
        for (AulaReceitaItemDTO receitaItemDTO : aulaDTO.getReceitas()) {
            Receita receita = receitaRepository.findById(receitaItemDTO.getReceitaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Receita não encontrada"));

            // 2b. Cria e associa AulaReceita
            AulaReceita aulaReceita = new AulaReceita();
            aulaReceita.setAula(aula);
            aulaReceita.setReceita(receita);
            aulaReceita.setQuantidade(receitaItemDTO.getQuantidade());
            aulaReceitaRepository.save(aulaReceita); // Salva a entidade de junção

            // Adiciona à lista da Aula
            aula.getReceitas().add(aulaReceita);

            // 3. Itera sobre os Produtos de CADA Receita
            for (ReceitaProduto rp : receita.getProdutos()) {
                Produto produto = rp.getProduto();

                // 3. Cálculo Transacional: Quantidade do Produto na Receita * Quantidade da Receita na Aula
                int quantidadeReceitaNaAula = receitaItemDTO.getQuantidade();
                int quantidadeProdutoNaReceita = rp.getQuantidade();

                int totalNecessario = quantidadeProdutoNaReceita * quantidadeReceitaNaAula;

                // Agrupa a quantidade total necessária por ID de Produto
                produtosParaRetirar.merge(produto.getId(), totalNecessario, Integer::sum);
            }
        }

        // 4. Verifica o estoque e realiza a retirada
        for (Map.Entry<Long, Integer> entry : produtosParaRetirar.entrySet()) {
            Long produtoId = entry.getKey();
            Integer totalNecessario = entry.getValue();

            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

            if (produto.getQuantidadeEstoque() < totalNecessario) {
                // Se o estoque for insuficiente, lança uma exceção e o @Transactional fará o rollback.
                throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            // Retira do estoque
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - totalNecessario);
            produtoRepository.save(produto); // Salva a alteração de estoque
        }

        return aula;
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

            dto.setReceitas(
                    aula.getReceitas().stream()
                            .map(ar -> new AulaReceitaItemDTO(ar.getReceita().getId(), ar.getQuantidade()))
                            .toList()
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

            dto.setReceitas(
                    aula.getReceitas().stream()
                            .map(ar -> new AulaReceitaItemDTO(ar.getReceita().getId(), ar.getQuantidade()))
                            .toList()
            );

            // ... (Mapeamento opcional de userId) ...

            return dto;
        }).toList();
    }

    @Transactional // Garante que a atualização seja atômica
    @Override
    public boolean updateAulaById(Long id, AulaDTO aulaDTO, UUID usuarioId) {
        return aulaRepository.findById(id).map(aula -> {
            // 1. Atualiza campos simples
            aula.setNome(aulaDTO.getNome()); // Adicionei o nome, que faltava
            aula.setDescricao(aulaDTO.getDescricao());
            aula.setData(aulaDTO.getData());
            aula.setInstrutor(aulaDTO.getInstrutor());
            aula.setMateria(aulaDTO.getMateria());
            aula.setAno(aulaDTO.getAno());
            aula.setSemestre(aulaDTO.getSemestre());
            aula.setModulo(aulaDTO.getModulo());
            aula.setPeriodo(aulaDTO.getPeriodo());

            // 2. Lógica de Atualização de Receitas e Estoque

            // REVERSÃO: Primeiro, calcule o impacto das receitas ANTIGAS e devolva o estoque
            Map<Long, Integer> produtosParaDevolver = new HashMap<>();
            for (AulaReceita ar : aula.getReceitas()) {
                int quantidadeReceitaNaAula = ar.getQuantidade();
                for (ReceitaProduto rp : ar.getReceita().getProdutos()) {
                    int totalDevolvido = rp.getQuantidade() * quantidadeReceitaNaAula;
                    produtosParaDevolver.merge(rp.getProduto().getId(), totalDevolvido, Integer::sum);
                }
            }

            // Devolve o estoque antes de apagar as relações
            for (Map.Entry<Long, Integer> entry : produtosParaDevolver.entrySet()) {
                Produto produto = produtoRepository.findById(entry.getKey()).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado ao devolver estoque."));
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + entry.getValue());
                produtoRepository.save(produto);
            }

            // LIMPAR AS RECEITAS ANTIGAS (O CascadeType.ALL em Aula tratará de apagar as entidades AulaReceita)
            aula.getReceitas().clear();
            aulaRepository.save(aula); // Salva a aula para garantir que a lista esteja vazia antes de adicionar

            // 3. RECRIAR (Lógica similar ao createAula)
            Map<Long, Integer> produtosParaRetirar = new HashMap<>();
            if (aulaDTO.getReceitas() != null) {
                for (AulaReceitaItemDTO receitaItemDTO : aulaDTO.getReceitas()) {
                    Receita receita = receitaRepository.findById(receitaItemDTO.getReceitaId())
                            .orElseThrow(() -> new ResourceNotFoundException("Receita não encontrada"));

                    // Cria e associa AulaReceita
                    AulaReceita aulaReceita = new AulaReceita();
                    aulaReceita.setAula(aula);
                    aulaReceita.setReceita(receita);
                    aulaReceita.setQuantidade(receitaItemDTO.getQuantidade());
                    // Não precisa de save explícito se o cascade estiver configurado, mas manter para clareza
                    aulaReceitaRepository.save(aulaReceita);

                    aula.getReceitas().add(aulaReceita); // Adiciona na lista da Aula

                    // Calcula o novo total para retirada
                    for (ReceitaProduto rp : receita.getProdutos()) {
                        int totalNecessario = rp.getQuantidade() * receitaItemDTO.getQuantidade();
                        produtosParaRetirar.merge(rp.getProduto().getId(), totalNecessario, Integer::sum);
                    }
                }
            }

            // 4. RETIRADA (Nova Verificação e Ajuste de Estoque)
            for (Map.Entry<Long, Integer> entry : produtosParaRetirar.entrySet()) {
                Long produtoId = entry.getKey();
                Integer totalNecessario = entry.getValue();

                Produto produto = produtoRepository.findById(produtoId)
                        .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

                if (produto.getQuantidadeEstoque() < totalNecessario) {
                    // Se faltar estoque, lança exceção e faz ROLLBACK de TODAS as alterações (incluindo a devolução)
                    throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + produto.getNome());
                }

                // Retira do estoque
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - totalNecessario);
                produtoRepository.save(produto);
            }

            // 5. Salva a Aula (as alterações no estoque e nas relações já foram salvas dentro do loop/transação)
            aulaRepository.save(aula);

            return true;
        }).orElse(false);
    }

    @Override
    public void deleteAulaById(Long id) {
        aulaRepository.deleteById(id);
    }
}