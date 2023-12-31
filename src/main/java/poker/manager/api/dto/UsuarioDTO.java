package poker.manager.api.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.domain.enums.UserRole;
import java.util.Set;

public record UsuarioDTO(
        @NotNull
        Integer id,
        @NotNull
        @Length(min = 10, max = 30, message = "Nome deve possuir entre 10 a 30 caracters.")
        String nome,
        @NotNull
        @Length(min = 5, max = 15, message = "Username deve possuir entre 5 a 15 caracters.")
        String username,
        @NotNull(message = "Preenchimento Obrigatório")
        String chavePix,
        String endereco,
        @Enumerated(EnumType.STRING)
        UserRole role, Boolean isEnabled, Set<UsuarioPartida> partidas) {

    public UsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(),usuario.getUsername(),
                usuario.getChavePix(), usuario.getEndereco(),
                usuario.getRole(), usuario.getEnabled(), usuario.getPartidas());
    }
}
