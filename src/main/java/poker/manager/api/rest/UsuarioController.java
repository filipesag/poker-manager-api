package poker.manager.api.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import poker.manager.api.domain.Usuario;
import poker.manager.api.dto.NovoUsuarioDTO;
import poker.manager.api.dto.UsuarioAtualizadoDTO;
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
    public ResponseEntity<UsuarioDTO> buscarUsuario(@PathVariable Integer id) {
        Usuario user =  usuarioService.buscarUsuario(id);
        UsuarioDTO usuarioDTO = new UsuarioDTO(user);
        return ResponseEntity.ok().body(usuarioDTO);
    }

    @GetMapping(value = "/find/all")
    public Page<UsuarioDTO> findAllEnabled(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        Page<UsuarioDTO> listDto = usuarioService.findAll(pageable).map(UsuarioDTO::new);
        return ResponseEntity.ok().body(listDto).getBody();
    }

    @PostMapping(value = "/new/player")
    public ResponseEntity<Usuario> inserirNovoUsuario(@RequestBody @Valid NovoUsuarioDTO newUserDTO) {
        Usuario usuario = new Usuario(newUserDTO);
        usuario = usuarioService.inserirNovoUsuario(usuario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUserDTO.id()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @PutMapping(value = "update/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody @Valid NovoUsuarioDTO userDTO) {
        Usuario usuario = new Usuario(userDTO);
        usuario = usuarioService.atualizarUsuario(usuario);
        return ResponseEntity.noContent().build();
    }
}
