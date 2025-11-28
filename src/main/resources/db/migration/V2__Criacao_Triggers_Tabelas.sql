-- V2_Criacao_Triggers_Estoque.sql
-- Este arquivo está inativo no Flyway (apenas um underscore)
-- Para ativar, renomeie para V2__Criacao_Triggers_Estoque.sql

-- 1. TRIGGER: AFTER INSERT (Adiciona ao estoque)
DELIMITER //

CREATE TRIGGER trg_entrada_produtos_insert
AFTER INSERT ON entrada_produto
FOR EACH ROW
BEGIN
    UPDATE produtos
    SET quantidade_estoque = quantidade_estoque + NEW.quantidade
    WHERE id = NEW.fk_produto_id;
END;
//

-- 2. TRIGGER: AFTER DELETE (Remove do estoque/Estorna)
CREATE TRIGGER trg_entrada_produtos_delete
AFTER DELETE ON entrada_produto
FOR EACH ROW
BEGIN
    -- OLD.quantidade representa a quantidade que estava na linha excluída
    UPDATE produtos
    SET quantidade_estoque = quantidade_estoque - OLD.quantidade
    WHERE id = OLD.fk_produto_id;
END;
//

-- 3. TRIGGER: AFTER UPDATE (Ajusta o estoque)
CREATE TRIGGER trg_entrada_produtos_update
AFTER UPDATE ON entrada_produto
FOR EACH ROW
BEGIN
    -- (NEW.quantidade - OLD.quantidade) é a diferença líquida que deve ser somada (ou subtraída, se for negativa)
    UPDATE produtos
    SET quantidade_estoque = quantidade_estoque + (NEW.quantidade - OLD.quantidade)
    WHERE id = NEW.fk_produto_id;
END;
//

DELIMITER ; -- Retorna o delimitador para o padrão (ponto e vírgula)