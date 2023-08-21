CREATE TABLE usuario(
    id INTEGER NOT NULL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    chavePix VARCHAR(40) NOT NULL,
    endereco VARCHAR(40) NOT NULL,
    isEnabled BOOLEAN NOT NULL,
    role VARCHAR(15) NOT NULL
);