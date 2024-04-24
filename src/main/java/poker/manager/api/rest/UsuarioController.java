package poker.manager.api.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import poker.manager.api.domain.Partida;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.dto.NovoUsuarioDTO;
import poker.manager.api.dto.PartidaDTO;
import poker.manager.api.dto.UsuarioDTO;
import poker.manager.api.dto.UsuarioPartidaDTO;
import poker.manager.api.service.UsuarioPartidaService;
import poker.manager.api.service.UsuarioService;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/v1/users")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioPartidaService usuarioPartidaService;

    @Autowired
    private BCryptPasswordEncoder encoder;

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

    @GetMapping(value = "/find/all-in-match")
    public Set<Usuario> findAllinStartedMatch() {
        Set<Usuario> usuarioSet = usuarioService.buscaTodosEmPartidaIniciada();
        return ResponseEntity.ok().body(usuarioSet).getBody();
    }

    @PostMapping(value = "/new/player")
    public ResponseEntity<Usuario> inserirNovoUsuario(@RequestBody @Valid NovoUsuarioDTO newUserDTO) {
        Usuario usuario = new Usuario(newUserDTO);
        usuario = usuarioService.inserirNovoUsuario(usuario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @PutMapping(value = "update/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody @Valid NovoUsuarioDTO userDTO) {
        Usuario usuario = new Usuario(userDTO);
        usuario = usuarioService.atualizarUsuario(usuario);
        return ResponseEntity.noContent().build();
    }


    @PutMapping(value = "/set/closed-match")
    public ResponseEntity<UsuarioPartida> inserirDadosPartidaFechada(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario(usuarioDTO);
        Set<UsuarioPartida> matches = usuario.getPartidas();
        for(UsuarioPartida x : matches) {
            if(x.getPartida().getStatus().toString() == "FINALIZADA") {
                UsuarioPartida usuarioPartida = new UsuarioPartida(x.getPartida(), usuario);
                usuarioPartida.setCancelado(x.getCancelado());
                usuarioPartida.setRebuy(x.getRebuy());
                usuarioPartida.setAnfitriao(x.getAnfitriao());
                usuarioPartida.setFichasFinal(x.getFichasFinal());
                System.out.println(x.getFichasFinal());
                usuarioPartida.setNetProFit(x.getNetProFit());
                usuarioPartida.setId(x.getId());
                usuarioPartidaService.inserirDadosFimDaPartida(usuarioPartida);
            }
        }
        return ResponseEntity.noContent().build();
    }


}
