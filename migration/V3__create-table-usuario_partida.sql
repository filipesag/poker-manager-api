CREATE TABLE IF NOT EXISTS usuario_partida(

    usuario_id INTEGER,
    partida_id INTEGER,
    PRIMARY KEY(usuario_id,partida_id),
    isRebuy BOOLEAN,
    colocacao INTEGER,
    netProfit FLOAT,
    fichasFinal INTEGER,
    isAnfitriao BOOLEAN
);