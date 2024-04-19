DROP TABLE usuario_partida;

CREATE TABLE IF NOT EXISTS usuario_partida(
    partida_id INTEGER,
    isRebuy BOOLEAN,
    colocacao INTEGER,
    netProfit FLOAT,
    fichasFinal INTEGER,
    isAnfitriao BOOLEAN,
    PRIMARY KEY(usuario_id, partida_id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (partida_id) REFERENCES partidas(id) ON DELETE CASCADE
);

ALTER TABLE usuario_partida DROP COLUMN partidaid;
ALTER TABLE usuario_partida DROP COLUMN usuarioid;