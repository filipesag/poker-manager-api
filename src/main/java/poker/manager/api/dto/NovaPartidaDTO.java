package poker.manager.api.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import poker.manager.api.domain.Partida;
import java.time.LocalDate;

public record NovaPartidaDTO(
    Integer id,
    @NotNull(message = "Valor do bucket é mandatório")
    Double bucketPorPessoa,
    Integer quantidadeJogadores,

    @NotNull(message = "Data da partida é mandatório")
    @Future(message = "Por favor, insira uma data futura!")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    LocalDate data) {
    public NovaPartidaDTO(Partida partida) {
        this(partida.getId(), partida.getBucketPorPessoa(), partida.getQuantidadeJogadores(), partida.getData());
    }
}


