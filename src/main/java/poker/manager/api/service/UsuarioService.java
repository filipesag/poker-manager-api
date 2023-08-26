package poker.manager.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import poker.manager.api.domain.Partida;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.repository.UsuarioRepository;

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
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuario = usuarioRepository.save(usuario);
        return usuario;
    }

    public Usuario atualizarUsuario(Usuario oldUser){
        Usuario NewUser = usuarioRepository.getReferenceById(oldUser.getId());
        atualizarDados(NewUser, oldUser);
        usuarioRepository.save(NewUser);
        return NewUser;
    }

    public void atualizarDados(Usuario oldUser,Usuario newUser){
        oldUser.setNome(newUser.getNome());
        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(encoder.encode(newUser.getPassword()));
        oldUser.setChavePix(newUser.getChavePix());
        oldUser.setEndereco(newUser.getEndereco());
    }

    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioRepository.findAllByIsEnabledTrue(pageable);
    }

}
