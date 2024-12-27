CREATE TABLE usuario(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO usuario(id, nome, email) VALUES(1, 'Ana da Silva', 'ana@email.com');
INSERT INTO usuario(id, nome, email) VALUES(2, 'Julio Cesar', 'julio@email.com');
