package poker.manager.api.dto;



import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.domain.enums.Permissao;
import java.util.Set;

public record NovoUsuarioDTO(Integer id,
        @NotEmpty
        @Length(min = 10, max = 30, message = "Nome deve possuir entre 10 a 30 caracters.")
        String nome,
        @NotEmpty
        @Length(min = 5, max = 15, message = "Username deve possuir entre 5 a 15 caracters.")
        String username,
        @NotEmpty
        @Length(min = 5, max = 15, message = "Password deve possuir entre 5 a 15 caracters.")
        String password,
        @NotEmpty(message = "Preenchimento Obrigatório")
        String chavePix,
        String endereco,
        @Enumerated(EnumType.STRING)
        Permissao role,
        Boolean isEnabled, Set<UsuarioPartida> partidas) {

    public NovoUsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getUsername(), usuario.getPassword(),
                usuario.getChavePix(), usuario.getEndereco(),
                usuario.getRole(), usuario.getEnabled(), usuario.getPartidas());
    }

}
