INSERT INTO usuario (nome, username, password,
 chavePix, endereco, isEnabled, role) VALUES(
 'Filipe Aguiar', 'filipesag', 'filipe123', 'filipe@hotmail.com',
 'Rua Bambuí 422, ap 302', true, 'ADMINISTRADOR'
);

INSERT INTO usuario (nome, username, password,
 chavePix, endereco, isEnabled, role) VALUES(
 'Thalison Neves', 'thalis', 'thalis123', 'thalis@hotmail.com',
 'Rua jaragua 422, ap 302', true, 'ADMINISTRADOR'
);

INSERT INTO usuario (nome, username, password,
 chavePix, endereco, isEnabled, role) VALUES(
 'Thaylon Luan', 'thaylin', 'thaylin123', 'thaylin@hotmail.com',
 'Rua coronel 422, ap 302', true, 'ADMINISTRADOR'
);

INSERT INTO usuario (nome, username, password,
 chavePix, endereco, isEnabled, role) VALUES(
 'Daniel Ezequiel', 'danielpsico', 'danielpsico', 'danielpsico@hotmail.com',
 'Rua Skinner 422, ap 302', true, 'USUARIO'
);

INSERT INTO usuario (nome, username, password,
 chavePix, endereco, isEnabled, role) VALUES(
 'Danton Boechat', 'danton', 'danton123', 'danton@hotmail.com',
 'Rua Cristiano Machado 422, ap 302', true, 'USUARIO'
);

INSERT INTO usuario (nome, username, password,
 chavePix, endereco, isEnabled, role) VALUES(
 'Vinicius Concenição', 'vinibjj', 'vinibjj123', 'vinibjj123@hotmail.com',
 'Rua São Pedro 422, ap 302', true, 'USUARIO'
);



INSERT INTO partida (usuarioAnfitriaoId, quantidadeJogadores, data, status) VALUES(
    2, 5, '23/09/2023', 'FINALIZADA'
);

INSERT INTO partida (usuarioAnfitriaoId, quantidadeJogadores, data, status) VALUES(
    2, 5, '25/10/2023', 'ABERTA'
);

INSERT INTO usuario_partida (partidaId, usuarioId, isRebuy, colocacao,
netProFit, fichasFinal, isAnfitriao, isCancelado) VALUES(
   1,1,true,3,0.00,1500,false,false
);

INSERT INTO usuario_partida (partidaId, usuarioId, isRebuy, colocacao,
netProFit, fichasFinal, isAnfitriao, isCancelado) VALUES(
   1,2,false,1,0.00,8900,false,false
);

INSERT INTO usuario_partida (partidaId, usuarioId, isRebuy, colocacao,
netProFit, fichasFinal, isAnfitriao, isCancelado) VALUES(
   1,3,true,2,0.00,3200,false,false
);

INSERT INTO usuario_partida (partidaId, usuarioId, isRebuy, colocacao,
netProFit, fichasFinal, isAnfitriao, isCancelado) VALUES(
   1,4,false,5,0.00,0,false,false
);

INSERT INTO usuario_partida (partidaId, usuarioId, isRebuy, colocacao,
netProFit, fichasFinal, isAnfitriao, isCancelado) VALUES(
   1,5,true,6,0.00,0,false,false
);

INSERT INTO usuario_partida (partidaId, usuarioId, isRebuy, colocacao,
netProFit, fichasFinal, isAnfitriao, isCancelado) VALUES(
   1,6,true,4,0.00,0,false,false
);

INSERT INTO usuario_partida (partidaId, usuarioId, isRebuy, colocacao,
netProFit, fichasFinal, isAnfitriao, isCancelado) VALUES(
   2,3,null,null,null,null,true,false
);

INSERT INTO usuario_partida (partidaId, usuarioId, isRebuy, colocacao,
netProFit, fichasFinal, isAnfitriao, isCancelado) VALUES(
   2,2,null,null,null,null,false,false
);