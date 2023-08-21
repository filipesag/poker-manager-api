CREATE TABLE partida(
    id INTEGER NOT NULL PRIMARY KEY,
    usuarioAnfitriaoId INTEGER NOT NULL,
    quantidadeJogadores INTEGER NOT NULL,
    data DATE,
    status VARCHAR(22)
);