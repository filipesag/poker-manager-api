INSERT INTO usuario (id, nome, username, password,
 chavePix, endereco, isEnabled, role) VALUES(1,
 'Filipe Aguiar', 'filipesag', 'filipe123', 'filipe@hotmail.com',
 'Rua Bambuí 422, ap 302', true, 'ADMINISTRADOR');

INSERT INTO usuario (id, nome, username, password,
 chavePix, endereco, isEnabled, role) VALUES(2,
 'Thalison Neves', 'thalis', 'thalis123', 'thalis@hotmail.com',
 'Rua jaragua 422, ap 302', true, 'ADMINISTRADOR'
);

INSERT INTO usuario (id, nome, username, password,
 chavePix, endereco, isEnabled, role) VALUES(3,
 'Thaylon Luan', 'thaylin', 'thaylin123', 'thaylin@hotmail.com',
 'Rua coronel 422, ap 302', true, 'ADMINISTRADOR'
);

INSERT INTO usuario (id, nome, username, password,
 chavePix, endereco, isEnabled, role) VALUES(4,
 'Daniel Ezequiel', 'danielpsico', 'danielpsico', 'danielpsico@hotmail.com',
 'Rua Skinner 422, ap 302', true, 'USUARIO'
);

INSERT INTO usuario (id, nome, username, password,
 chavePix, endereco, isEnabled, role) VALUES(5,
 'Danton Boechat', 'danton', 'danton123', 'danton@hotmail.com',
 'Rua Cristiano Machado 422, ap 302', true, 'USUARIO'
);

INSERT INTO usuario (id, nome, username, password,
 chavePix, endereco, isEnabled, role) VALUES(6,
 'Vinicius Concenição', 'vinibjj', 'vinibjj123', 'vinibjj123@hotmail.com',
 'Rua São Pedro 422, ap 302', true, 'USUARIO'
);


INSERT INTO partida (id, usuarioAnfitriaoId, quantidadeJogadores, data, status) VALUES(1,
    2, 5, '2023-09-12', 'ABERTA'
);
