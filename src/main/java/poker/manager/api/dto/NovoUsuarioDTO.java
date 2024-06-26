package poker.manager.api.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.domain.enums.UserRole;
import java.util.Set;

public record NovoUsuarioDTO(

        Integer id,
        @NotEmpty(message = "Nome precisa ser preenchido")
        @Length(min = 10, max = 30, message = "Nome deve possuir entre 10 a 30 caracters.")
        String nome,
        @NotEmpty(message = "Username precisa ser preenchida")
        @Length(min = 5, max = 15, message = "Username deve possuir entre 5 a 15 caracters.")
        String username,
        @NotEmpty(message = "Senha precisa ser preenchida")
        @Length(min = 5, max = 15, message = "Password deve possuir entre 5 a 15 caracters.")
        String password,
        @NotEmpty(message = "Preenchimento Obrigatório")
        String chavePix,
        String endereco,

        Boolean isEnabled,
        @Enumerated(EnumType.STRING)
        UserRole role,
        Set<UsuarioPartida> partidas) {


    public NovoUsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getUsername(), usuario.getPassword(),
                usuario.getChavePix(), usuario.getEndereco(),
                usuario.getEnabled(),usuario.getRole(), usuario.getPartidas());
    }

}
