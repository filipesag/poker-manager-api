package poker.manager.api.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import poker.manager.api.domain.Usuario;
import poker.manager.api.dto.NovoUsuarioDTO;
import poker.manager.api.dto.UsuarioDTO;
import poker.manager.api.service.UsuarioService;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("users")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = "find/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Integer id) {
        Usuario user =  usuarioService.buscarUsuario(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(value = "/find/all")
    public ResponseEntity<List<Usuario>> findAllEnabled() {
        List<UsuarioDTO> users = usuarioService.findAll().stream().map(UsuarioDTO::new).toList();
        List<Usuario> listDto = users.stream().map(x -> new Usuario()).toList();
        return ResponseEntity.ok().body(listDto);
    }

    @PostMapping(value = "/new/player")
    public ResponseEntity<Usuario> inserirNovoUsuario(@RequestBody @Valid NovoUsuarioDTO newUserDTO) {
        Usuario user = usuarioService.inserirNovoUsuario(new Usuario(newUserDTO.id(), newUserDTO.nome(),
                newUserDTO.username(), newUserDTO.password(), newUserDTO.chavePix(), newUserDTO.endereco(), newUserDTO.role(),true,null));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping(value = "update/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Integer id, @RequestBody @Valid UsuarioDTO userDTO) {
        Usuario user = usuarioService.fromDTO(userDTO);
        user.setId(id);
        user = usuarioService.atualizarUsuario(id, user);
        return ResponseEntity.noContent().build();
    }
}
