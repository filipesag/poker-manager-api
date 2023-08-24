package poker.manager.api.dto;

import poker.manager.api.domain.Usuario;

public record UsuarioLoginDTO(String username, String password) {

    public UsuarioLoginDTO(Usuario usuario) {
        this(usuario.getUsername(), usuario.getPassword());
    }
}
