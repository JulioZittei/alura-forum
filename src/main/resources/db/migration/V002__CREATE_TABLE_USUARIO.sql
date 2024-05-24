CREATE TABLE usuario(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    email varchar(50) NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO usuario VALUES(1, 'Ana da Silva', 'ana@email.com');
