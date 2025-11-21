package com.fiec.projeto_back_gastroflow.features.products.models;


import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de representação (Bean) para mapear os campos do arquivo CSV de Produtos.
 * O OpenCSV usará esta classe para converter cada linha do CSV em um objeto.
 */
@Data
@NoArgsConstructor
public class ProdutoCSVRepresentation {

    // Mapeamento do nome do produto no CSV para o campo 'nome'
    @CsvBindByName(column = "nome", required = true)
    private String nome;

    // Mapeamento da categoria. O valor lido será uma String (ex: "estocaveis")
    // e você precisará convertê-lo para o seu Enum Categoria posteriormente.
    @CsvBindByName(column = "categoria", required = true)
    private String categoria;

    // Mapeamento da quantidade em estoque. O valor lido será um Integer.
    @CsvBindByName(column = "quantidadeEstoque", required = true)
    private Integer quantidadeEstoque;

    // Mapeamento da unidade de medida. O valor lido será uma String (ex: "g")
    // e você precisará convertê-lo para o seu Enum UnidadeMedida posteriormente.
    @CsvBindByName(column = "unidadeMedida", required = true)
    private String unidadeMedida;
}
