CREATE TABLE resposta(
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensagem VARCHAR(300) NOT NULL,
    data_criacao DATETIME NOT NULL, 
    topico_id BIGINT NOT NULL, 
    autor_id BIGINT NOT NULL, 
    solucao INT(1) NOT NULL, 
    PRIMARY KEY(id),
    FOREIGN KEY (topico_id) REFERENCES topico(id),
    FOREIGN KEY (autor_id) REFERENCES usuario(id)
);
