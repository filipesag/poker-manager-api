package poker.manager.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import poker.manager.api.domain.Partida;
import poker.manager.api.domain.Usuario;
import poker.manager.api.dto.PartidaDTO;
import poker.manager.api.dto.UsuarioDTO;
import poker.manager.api.service.PartidaService;
import poker.manager.api.service.UsuarioPartidaService;
import poker.manager.api.domain.UsuarioPartida;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("match_users")
public class UsuarioPartidaController {
    @Autowired
    private UsuarioPartidaService usuarioPartidaService;

    @Autowired
    private PartidaService partidaService;


    @PostMapping(value = "/user/confirmation")
    public ResponseEntity<UsuarioPartida> confirmarPresenca(@RequestBody PartidaDTO partidaDTO, UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario(usuarioDTO);
        Partida partida = new Partida(partidaDTO);
        UsuarioPartida usuarioPartida = usuarioPartidaService.confirmarPresenca(partida, usuario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuarioPartida.getId()).toUri();
        return ResponseEntity.created(uri).body(usuarioPartida);
    }

    @PutMapping(value = "/calloff")
    public ResponseEntity<UsuarioPartida> cancelarPresenca(@RequestBody UsuarioDTO usuarioDTO, PartidaDTO partidaDTO) {
        Usuario usuario = new Usuario(usuarioDTO);
        UsuarioPartida usuarioPartida = usuarioPartidaService.cancelarPresenca(usuario, partidaDTO.id());
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/find/all")
    public ResponseEntity<List<UsuarioPartida>>buscarTudo(){
        List<UsuarioPartida> usuarioPartida = usuarioPartidaService.buscarTudo();
        return ResponseEntity.ok().body(usuarioPartida);
    }
}
