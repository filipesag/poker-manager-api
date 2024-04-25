package poker.manager.api.dto;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import poker.manager.api.domain.Partida;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.domain.enums.PartidaStatus;
import java.time.LocalDate;
import java.util.Set;

public record PartidaDTO(

        Integer id,
        @NotNull(message = "Valor do bucket é mandatório")
        Double bucketPorPessoa,
        Integer usuarioAnfitriaoId,
        Integer quantidadeJogadores,

        @NotNull(message = "Data da partida é mandatório")
        @Future(message = "Por favor, insira uma data futura!")
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        LocalDate data, PartidaStatus status,
        Set<UsuarioPartida> jogadores) {

    public PartidaDTO(Partida partida) {
        this(partida.getId(), partida.getBucketPorPessoa(), partida.getUsuarioAnfitriaoId(), partida.getQuantidadeJogadores(),
                partida.getData(),partida.getStatus(), partida.getJogadores());
    }

}
