package poker.manager.api.dto;

import poker.manager.api.domain.Partida;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.domain.enums.PartidaStatus;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.Set;

public record PartidaDTO(Integer id, Double bucketPorPessoa, Integer usuarioAnfitriaoId,
                         Integer quantidadeJogadores, LocalDate data, PartidaStatus status,
                         Set<UsuarioPartida> jogadores) {

    public PartidaDTO(Partida partida) {
        this(partida.getId(), partida.getBucketPorPessoa(), partida.getUsuarioAnfitriaoId(), partida.getQuantidadeJogadores(),
                partida.getData(),partida.getStatus(), partida.getJogadores());
    }

}
