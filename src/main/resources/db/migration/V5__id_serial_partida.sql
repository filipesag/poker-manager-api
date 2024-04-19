CREATE TABLE IF NOT EXISTS partida(
    id SERIAL PRIMARY KEY,
    usuarioAnfitriaoId INTEGER NOT NULL,
    quantidadeJogadores INTEGER NOT NULL,
    data DATE,
    status VARCHAR(22)
);