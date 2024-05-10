CREATE TABLE IF NOT EXISTS partida(
    id INTEGER NOT NULL PRIMARY KEY,
    bucketPorPessoa FLOAT NOT NULL,
    usuarioAnfitriaoId INTEGER NOT NULL,
    quantidadeJogadores INTEGER NOT NULL,
    data DATE,
    status VARCHAR(22)
);

INSERT INTO partida (id, bucketPorPessoa, usuarioAnfitriaoId, quantidadeJogadores, data, status)
VALUES(1,20.0,2, 5, '25/10/2023', 'ABERTA'
);