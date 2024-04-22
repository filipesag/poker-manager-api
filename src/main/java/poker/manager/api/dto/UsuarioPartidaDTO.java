package poker.manager.api.dto;

import poker.manager.api.domain.UsuarioPartida;

public record UsuarioPartidaDTO(
        Integer usuario,
        Integer partida,
        Boolean isRebuy,
        Integer colocacao,
        Double netProFit,
        Integer fichasFinal,
        Boolean isAnfitriao,
        Boolean isCancelado) {

    public UsuarioPartidaDTO(UsuarioPartida usuarioPartida) {
        this(usuarioPartida.getUsuario().getId(), usuarioPartida.getPartida().getId(), usuarioPartida.getRebuy(),
                usuarioPartida.getColocacao(), usuarioPartida.getNetProFit(), usuarioPartida.getFichasFinal(), usuarioPartida.getAnfitriao(),
                usuarioPartida.getCancelado());
    }
}
