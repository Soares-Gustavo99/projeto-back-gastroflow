package com.fiec.projeto_back_gastroflow.features.products.services.impl;


import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoDTO;
import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoPagedResponseDTO;
import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoSearch;
import com.fiec.projeto_back_gastroflow.features.products.models.*;
import com.fiec.projeto_back_gastroflow.features.products.repositories.ProdutoRepository;
import com.fiec.projeto_back_gastroflow.features.products.services.ProdutoService;
import com.fiec.projeto_back_gastroflow.features.products.models.ProdutoCSVRepresentation;
import com.fiec.projeto_back_gastroflow.utils.ImageUtils;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
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


    @Override
    @Transactional
    public void createProductsFromCsv(InputStream inputStream) {

        List<ProdutoCSVRepresentation> produtosCsv = new ArrayList<>();

        // 1. LEITURA E PARSING DO CSV (Usando OpenCSV)
        // O InputStream já é o parâmetro, podemos usá-lo diretamente
        try (Reader reader = new InputStreamReader(inputStream, "UTF-8")) {

            CsvToBean<ProdutoCSVRepresentation> csvToBean = new CsvToBeanBuilder<ProdutoCSVRepresentation>(reader)
                    .withType(ProdutoCSVRepresentation.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSkipLines(0)
                    .build();

            produtosCsv = csvToBean.parse();
        } catch (Exception e) {
            // Falha na leitura do arquivo (IO, formato do CSV, etc.)
            throw new RuntimeException("Falha ao processar o arquivo CSV: " + e.getMessage(), e);
        }

        // 2. CONVERSÃO E PERSISTÊNCIA DOS DADOS
        try {
            for (ProdutoCSVRepresentation csvProduto : produtosCsv) {

                // Converte as strings para ENUMS
                // Importante: Verifique se a capitalização (toUpperCase/toLowerCase)
                // está correta em relação aos nomes dos seus Enums Java.
                Categoria categoria = Categoria.valueOf(csvProduto.getCategoria().toLowerCase());
                UnidadeMedida unidadeMedida = UnidadeMedida.valueOf(csvProduto.getUnidadeMedida().toLowerCase());

                // Cria e popula a entidade Produto
                Produto produto = new Produto();
                produto.setNome(csvProduto.getNome());
                produto.setQuantidadeEstoque(csvProduto.getQuantidadeEstoque());
                produto.setCategoria(categoria);
                produto.setUnidadeMedida(unidadeMedida);

                produtoRepository.save(produto);
            }
        } catch (IllegalArgumentException e) {
            // Captura se o valor da Categoria ou UnidadeMedida no CSV for inválido
            String erroMsg = "Valor inválido no CSV para Categoria ou Unidade de Medida. Erro: " + e.getMessage();
            throw new RuntimeException(erroMsg, e);
        } catch (Exception ex) {
            // Outras exceções durante a persistência
            throw new RuntimeException("Erro ao salvar produtos no banco de dados: " + ex.getMessage(), ex);
        }
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
    public ProdutoPagedResponseDTO findAllWithQueries(ProdutoSearch produtoSearch) {
        Page<Produto> produtoResponse = produtoRepository.findProdutos(produtoSearch);


        if (CollectionUtils.isEmpty(produtoResponse.getContent())) {
            return new ProdutoPagedResponseDTO();
        } else {
            List<ProdutoDTO> produtosDTOS =
             produtoResponse.getContent().stream()
                    .map(produto -> ProdutoDTO.builder()
                            .id(produto.getId())
                            .nome(produto.getNome())
                            .categoria(produto.getCategoria())
                            .quantidadeEstoque(produto.getQuantidadeEstoque())
                            .unidadeMedida(produto.getUnidadeMedida())
                            .validade(produto.getValidade())
                            .build()
                    )
                    .toList();
            ProdutoPagedResponseDTO produtoPagedResponseDTO = new ProdutoPagedResponseDTO();
            produtoPagedResponseDTO.setProdutos(produtosDTOS);
            produtoPagedResponseDTO.setTotal(produtoResponse.getTotalElements());
            produtoPagedResponseDTO.setTotalPages((long) produtoResponse.getTotalPages());
            return produtoPagedResponseDTO;
        }
    }

    // Deletar Produto por ID
    public void deleteProdutoById(Long id) {
        produtoRepository.deleteById(id);
    }
}

