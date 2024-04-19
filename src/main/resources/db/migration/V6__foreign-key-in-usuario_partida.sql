
CREATE TABLE IF NOT EXISTS usuario(
    id SERIAL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    chavePix VARCHAR(40) NOT NULL,
    endereco VARCHAR(40) NOT NULL,
    isEnabled BOOLEAN NOT NULL,
    role VARCHAR(15) NOT NULL
);

CREATE TABLE IF NOT EXISTS partida(
    id SERIAL PRIMARY KEY,
    usuarioAnfitriaoId INTEGER NOT NULL,
    quantidadeJogadores INTEGER NOT NULL,
    data DATE,
    status VARCHAR(22)
);

CREATE TABLE IF NOT EXISTS usuario_partida(

    usuario_id INTEGER,
    partida_id INTEGER,
    PRIMARY KEY(usuario_id,partida_id),
    isRebuy BOOLEAN,
    colocacao INTEGER,
    netProfit FLOAT,
    fichasFinal INTEGER,
    isAnfitriao BOOLEAN,

    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (partida_id) REFERENCES partida(id)
);