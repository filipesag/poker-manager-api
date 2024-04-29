package poker.manager.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import poker.manager.api.domain.Partida;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.repository.UsuarioRepository;
import poker.manager.api.service.exceptions.UsuarioUsernameUsedByAnotherUserException;

import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;


    public Usuario buscarUsuario(Integer id){
        Optional<Usuario> user = usuarioRepository.findById(id);
        return user.get();
    }

    public Usuario inserirNovoUsuario(Usuario usuario){
        Optional<Usuario> usernameWithUsername = usuarioRepository.findByUsername(usuario.getUsername());
        if (!usernameWithUsername.isEmpty()) {
            throw new UsuarioUsernameUsedByAnotherUserException();
        }
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuario = usuarioRepository.save(usuario);
        return usuario;
    }

    public Usuario atualizarUsuario(Usuario user){
        Optional<Usuario> newUser = usuarioRepository.findById(user.getId());
        atualizarDados(newUser.get(), user);
        usuarioRepository.save(newUser.get());
        return newUser.get();
    }

    public void atualizarDados(Usuario oldUser,Usuario newUser){
        Optional<Usuario> userWithUsername = usuarioRepository.findByUsername(newUser.getUsername());
        if(userWithUsername.isPresent() && userWithUsername.get().getId() != oldUser.getId()) {
            throw new UsuarioUsernameUsedByAnotherUserException();
        }
        oldUser.setNome(newUser.getNome());
        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(encoder.encode(newUser.getPassword()));
        oldUser.setChavePix(newUser.getChavePix());
        oldUser.setEndereco(newUser.getEndereco());
    }

    public Set<Usuario> buscaTodosEmPartidaIniciada() {
        Set<Usuario> users = usuarioRepository.findAllInMatchStarted();
        return users;
    }

    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioRepository.findAllByIsEnabledTrue(pageable);
    }

}
