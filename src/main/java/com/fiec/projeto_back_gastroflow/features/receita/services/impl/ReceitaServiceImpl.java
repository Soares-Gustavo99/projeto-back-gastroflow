package com.fiec.projeto_back_gastroflow.features.receita.services.impl;

import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import com.fiec.projeto_back_gastroflow.features.products.repositories.ProdutoRepository;
import com.fiec.projeto_back_gastroflow.features.receita.dto.ReceitaDTO;
import com.fiec.projeto_back_gastroflow.features.receita.dto.ReceitaProdutoItemDTO;
import com.fiec.projeto_back_gastroflow.features.receita.models.Receita;
import com.fiec.projeto_back_gastroflow.features.receita.repositories.ReceitaRepository;
import com.fiec.projeto_back_gastroflow.features.receita.services.ReceitaService;
import com.fiec.projeto_back_gastroflow.features.receitaProduto.ReceitaProduto;
import com.fiec.projeto_back_gastroflow.features.receitaProduto.ReceitaProdutoId;
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
        // ... (mapeamento de outros campos) ...
        receita.setNome(receitaDTO.getNome()); //
        receita.setDescricao(receitaDTO.getDescricao());
        receita.setTempoPreparo(receitaDTO.getTempoPreparo());
        receita.setRendimento(receitaDTO.getRendimento());
        receita.setTipo(receitaDTO.getTipo());
        receita.setProfessorReceita(receitaDTO.getProfessorReceita());
        //
        // ... (outros mapeamentos) ...

        // Relacionar usuário
        User user = userRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Erro de Autenticação: Usuário logado não encontrado.")); //
        receita.setUser(user); //

        // >>> MUDANÇA CRÍTICA AQUI: Mapear DTOs para ReceitaProduto Entities <<<
        if (receitaDTO.getProdutos() != null && !receitaDTO.getProdutos().isEmpty()) {

            // 1. Coleta todos os IDs de produto para buscar todos de uma vez (otimização)
            List<Long> produtoIds = receitaDTO.getProdutos().stream()
                    .map(ReceitaProdutoItemDTO::getProdutoId)
                    .toList();

            // 2. Busca todos os Produtos de uma vez
            List<Produto> produtosExistentes = produtoRepository.findAllById(produtoIds);

            // Mapeia IDs para Produtos para acesso rápido
            java.util.Map<Long, Produto> produtoMap = produtosExistentes.stream()
                    .collect(java.util.stream.Collectors.toMap(Produto::getId, p -> p));


            // 3. Cria a lista de ReceitaProduto
            List<ReceitaProduto> itensReceita = receitaDTO.getProdutos().stream()
                    .map(itemDto -> {
                        Produto produto = produtoMap.get(itemDto.getProdutoId());
                        if (produto == null) {
                            throw new RuntimeException("Produto com ID " + itemDto.getProdutoId() + " não encontrado.");
                        }

                        ReceitaProduto item = new ReceitaProduto();

                        // IMPORTANTE: O ID da Receita (receitaId) só existe após o primeiro save,
                        // mas o JPA consegue inferir o mapeamento.
                        // Para garantir a bidirecionalidade e o save em cascata:
                        item.setReceita(receita);
                        item.setProduto(produto);
                        item.setQuantidade(itemDto.getQuantidade());

                        // O ReceitaProdutoId só pode ser totalmente preenchido com o ID da Receita
                        // APÓS o save, mas o JPA é inteligente o suficiente para fazer isso em cascata
                        // se a relação for definida corretamente. É mais seguro setar o ID aqui
                        // para a bidirecionalidade.
                        ReceitaProdutoId id = new ReceitaProdutoId();
                        id.setProdutoId(produto.getId());
                        // O ID da Receita será preenchido pelo JPA após o save.
                        item.setId(id);

                        return item;
                    }).toList();

            // 4. Seta a lista de ReceitaProduto na Receita (sempre limpa a lista antiga)
            receita.setProdutos(itensReceita);
        }
        // FIM DA MUDANÇA CRÍTICA

        receitaRepository.save(receita); //
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

            dto.setProdutos(
                    receita.getProdutos().stream()
                            .map(rp -> new ReceitaProdutoItemDTO(rp.getProduto().getId(), rp.getQuantidade()))
                            .toList()
            );
            // FIM DA MUDANÇA CRÍTICA

            return dto; //
        }).orElse(null); //
    }

    @Override
    public List<ReceitaDTO> findAll() {
        return receitaRepository.findAll().stream().map(receita -> {
            ReceitaDTO dto = new ReceitaDTO();
            dto.setId(receita.getId());
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

            dto.setProdutos(
                    receita.getProdutos().stream()
                            .map(rp -> new ReceitaProdutoItemDTO(rp.getProduto().getId(), rp.getQuantidade()))
                            .toList()
            );
            // FIM DA MUDANÇA CRÍTICA

            return dto; //
        }).toList(); //
    }

    @Override
    public boolean updateReceitaById(Long id, ReceitaDTO receitaDTO, java.util.UUID usuarioId) {
        return receitaRepository.findById(id).map(receita -> {
            // ... (mapeamento de outros campos) ...
            receita.setNome(receitaDTO.getNome());
            receita.setDescricao(receitaDTO.getDescricao());
            receita.setNome(receitaDTO.getNome());
            receita.setTempoPreparo(receitaDTO.getTempoPreparo());
            receita.setRendimento(receitaDTO.getRendimento());
            receita.setTipo(receitaDTO.getTipo());
            receita.setProfessorReceita(receitaDTO.getProfessorReceita());//
            // ... (outros mapeamentos) ...

            // >>> MUDANÇA CRÍTICA AQUI: Lógica de Atualização para a nova relação <<<
            // Atualizar produtos
            if (receitaDTO.getProdutos() != null) {

                // Limpa a lista existente. Graças ao orphanRemoval=true, o JPA deletará os antigos registros na tabela receita_produto.
                receita.getProdutos().clear();

                // 1. Coleta todos os IDs de produto para buscar todos de uma vez (otimização)
                List<Long> produtoIds = receitaDTO.getProdutos().stream()
                        .map(ReceitaProdutoItemDTO::getProdutoId)
                        .toList();

                // 2. Busca todos os Produtos de uma vez
                List<Produto> produtosExistentes = produtoRepository.findAllById(produtoIds);

                // Mapeia IDs para Produtos para acesso rápido
                java.util.Map<Long, Produto> produtoMap = produtosExistentes.stream()
                        .collect(java.util.stream.Collectors.toMap(Produto::getId, p -> p));


                // 3. Cria a lista de ReceitaProduto (Lógica similar ao createReceita)
                List<ReceitaProduto> itensReceita = receitaDTO.getProdutos().stream()
                        .map(itemDto -> {
                            Produto produto = produtoMap.get(itemDto.getProdutoId());
                            if (produto == null) {
                                throw new RuntimeException("Produto com ID " + itemDto.getProdutoId() + " não encontrado.");
                            }

                            ReceitaProduto item = new ReceitaProduto();

                            // O ID da Receita já existe
                            ReceitaProdutoId item_id = new ReceitaProdutoId(receita.getId(), produto.getId());

                            // Seta todos os campos da entidade relacional
                            item.setId(item_id);
                            item.setReceita(receita);
                            item.setProduto(produto);
                            item.setQuantidade(itemDto.getQuantidade());

                            return item;
                        }).toList();

                // 4. Adiciona todos os novos itens na lista (o JPA salvará em cascata)
                receita.getProdutos().addAll(itensReceita);
            }
            // FIM DA MUDANÇA CRÍTICA

            // Atualiza o campo usuarioAlteracao automaticamente
            receita.setUsuarioAlteracao(usuarioId.toString()); //

            receitaRepository.save(receita); //
            return true;
        }).orElse(false); //
    }

    @Override
    public void deleteReceitaById(Long id) {
        receitaRepository.deleteById(id);
    }
}
