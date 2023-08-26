package poker.manager.api.dto;

import poker.manager.api.domain.Partida;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.domain.UsuarioPartidaPK;

import java.util.Set;

public record UsuarioPartidaDTO(
        Usuario usuario,
        Partida partida,
        Boolean isRebuy,
        Integer colocacao,
        Double netProFit,
        Integer fichasFinal,
        Boolean isAnfitriao,
        Boolean isCancelado) {

    public UsuarioPartidaDTO(UsuarioPartida usuarioPartida) {
        this(usuarioPartida.getId().getUsuario(), usuarioPartida.getId().getPartida(), usuarioPartida.getRebuy(),
                usuarioPartida.getColocacao(), usuarioPartida.getNetProFit(), usuarioPartida.getFichasFinal(), usuarioPartida.getAnfitriao(),
                usuarioPartida.getCancelado());
    }
}
