package poker.manager.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import poker.manager.api.domain.Usuario;
import poker.manager.api.dto.NovoUsuarioDTO;
import poker.manager.api.dto.UsuarioDTO;
import poker.manager.api.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;


    public Usuario buscarUsuario(Integer id){
        Optional<Usuario> users = usuarioRepository.findById(id);
        return users.get();
    }


    public Usuario inserirNovoUsuario(Usuario user){
        user = usuarioRepository.save(user);
        return user;
    }

    public Usuario atualizarUsuario(Integer id, Usuario newUser){
        Usuario Olduser = usuarioRepository.getReferenceById(id);
        atualizarDados(Olduser, newUser);
        return usuarioRepository.save(newUser);
    }

    public void atualizarDados(Usuario newUser, Usuario oldUser){
        newUser.setId(newUser.getId());
        newUser.setNome(oldUser.getNome());
        newUser.setEndereco(oldUser.getEndereco());
        newUser.setChavePix(oldUser.getChavePix());
        newUser.setPassword(oldUser.getPassword());
        newUser.setUsername(oldUser.getUsername());
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAllByIsEnabledTrue();
    }

    public Usuario fromDTO(UsuarioDTO usuarioDTO) {
        return new Usuario(usuarioDTO.id(), usuarioDTO.nome(), usuarioDTO.username(),null , usuarioDTO.chavePix(), usuarioDTO.endereco(),usuarioDTO.role(),usuarioDTO.isEnabled(),usuarioDTO.partidas());
    }

    public Usuario fromNewDTO(NovoUsuarioDTO newUserDto) {
        return new Usuario(newUserDto.id(), newUserDto.nome(), newUserDto.username(), encoder.encode(newUserDto.password()), newUserDto.chavePix(),
                newUserDto.endereco(),newUserDto.role(),newUserDto.isEnabled(),null);
    }

}
