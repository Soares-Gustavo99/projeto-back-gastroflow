-- V1_Criacao_Tabelas_Produtos_E_Entrada.sql
-- ESTE ARQUIVO FOI ATUALIZADO para incluir todos os campos das suas tabelas.
-- Para ativar, renomeie para V1__Criacao_Tabelas_Produtos_E_Entrada.sql

-- Tabela 1: produtos (Estoque)
CREATE TABLE produtos (
    id BIGINT PRIMARY KEY, -- Usando BIGINT conforme sua imagem
    categoria TINYINT,
    nome VARCHAR(255) NOT NULL,
    picture VARCHAR(255),
    quantidade_estoque INT NOT NULL DEFAULT 0, -- Campo que será atualizado pelos triggers
    unidade_medida TINYINT,
    validade DATETIME(6)
);

-- Tabela 2: entradas (Cabeçalho da Transação de Entrada)
-- Esta tabela guarda quem, quando e onde a entrada foi registrada.
CREATE TABLE entradas (
    id BIGINT PRIMARY KEY,
    data_entrada DATETIME(6) NOT NULL,
    observacao VARCHAR(255),
    fornecedor_id BIGINT,
    user_id BINARY(16) NOT NULL -- BINARY(16) conforme sua imagem
);

-- Tabela 3: entrada_produto (Itens da Entrada de Produto)
-- Tabela N:M que relaciona 'produtos' e 'entradas'.
-- É nesta tabela que os triggers operam.
CREATE TABLE entrada_produto (
    -- O Flyway não permite chaves compostas em migrations simples.
    -- Vamos usar as FKs como PKs (Chave Primária Composta Implícita/Covering Index)
    fk_entrada_id BIGINT NOT NULL,
    fk_produto_id BIGINT NOT NULL, -- Chave usada pelos triggers
    quantidade INT NOT NULL, -- Campo usado pelos triggers
    preco DECIMAL(38,2)

    -- Definindo a chave primária composta
    PRIMARY KEY (fk_entrada_id, fk_produto_id),

    -- Definições das Chaves Estrangeiras
    CONSTRAINT fk_ep_entrada
        FOREIGN KEY (fk_entrada_id)
        REFERENCES entradas(id)
        ON DELETE CASCADE, -- Se a entrada for deletada, seus itens somem

    CONSTRAINT fk_ep_produto
        FOREIGN KEY (fk_produto_id)
        REFERENCES produtos(id)
        ON DELETE RESTRICT
);