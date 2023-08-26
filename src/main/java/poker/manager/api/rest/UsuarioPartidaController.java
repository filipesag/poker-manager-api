package poker.manager.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import poker.manager.api.domain.Partida;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartidaPK;
import poker.manager.api.dto.PartidaDTO;
import poker.manager.api.dto.UsuarioDTO;
import poker.manager.api.dto.UsuarioPartidaDTO;
import poker.manager.api.service.PartidaService;
import poker.manager.api.service.UsuarioPartidaService;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.service.UsuarioService;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@RequestMapping("match_users")
public class UsuarioPartidaController {
    @Autowired
    private UsuarioPartidaService usuarioPartidaService;

    @Autowired
    private PartidaService partidaService;

    @Autowired
    private UsuarioService usuarioService;




    @GetMapping(value = "/find/all")
    public ResponseEntity<List<UsuarioPartida>>buscarTudo(){
        List<UsuarioPartida> usuarioPartida = usuarioPartidaService.buscarTudo();
        return ResponseEntity.ok().body(usuarioPartida);
    }
}
