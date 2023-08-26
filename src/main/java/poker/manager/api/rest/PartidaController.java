package poker.manager.api.rest;


import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poker.manager.api.domain.Partida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.dto.PartidaDTO;
import poker.manager.api.dto.UsuarioDTO;
import poker.manager.api.service.PartidaService;
import poker.manager.api.service.UsuarioPartidaService;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/match")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @Autowired
    private UsuarioPartidaService usuarioPartidaService;


    @GetMapping(value = "/{id}")
    public ResponseEntity<Partida> buscarPartida(@PathVariable Integer id) {
        Partida partida = partidaService.buscarPartida(id);
        return ResponseEntity.ok().body(partida);
    }

    @Transactional
    @PostMapping(value = "/creation")
    public ResponseEntity<Partida> criarNovaPartida(@RequestBody @DateTimeFormat(pattern = "dd/MM/yyyy") PartidaDTO partidaDTO) {
        Partida partida = new Partida(partidaDTO);
        partida = partidaService.criarPartida(partida);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(partida.getId()).toUri();
        return ResponseEntity.created(uri).body(partida);
    }


    @PutMapping(value = "/host")
    public ResponseEntity<Partida> cadastrarAnfitriao(@RequestBody PartidaDTO partidaDTO) {
        Partida partida = new Partida(partidaDTO);
        partidaService.cadastrarAnfitriao(partida);
        return ResponseEntity.noContent().build();
    }


    @PutMapping(value = "/start-match")
    public ResponseEntity<Partida> iniciarPartida(@RequestBody PartidaDTO partidaDTO){
        Partida partida = new Partida(partidaDTO);
        partidaService.iniciarPartida(partida);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/calloff-match")
    public ResponseEntity<Partida> cancelarPartida(@RequestBody PartidaDTO partidaDTO) {
        Partida partida = new Partida(partidaDTO);
        partidaService.cancelarPartida(partida);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/close-match")
    public ResponseEntity<Partida> finalizarPartida(@RequestBody PartidaDTO partidaDTO) {
        Partida partida = new Partida(partidaDTO);
        partidaService.finalizarPartida(partida);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/user/confirmation")
    public ResponseEntity<Partida> confirmarPresenca(@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario(usuarioDTO);
        Partida partida = partidaService.buscarPorStatusAberta();
        Integer quantidadeDeJogadores = partida.getQuantidadeJogadores();
        Integer numeroDeJogadaoresNaMesa = usuarioPartidaService.obterNumeroJogadores(partida.getId());
        if (numeroDeJogadaoresNaMesa < quantidadeDeJogadores) {
            usuarioPartidaService.confirmarPresenca(partida, usuario);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/calloff")
    public ResponseEntity<Partida> cancelarPresenca(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario(usuarioDTO);
        Partida partida = partidaService.buscarPorStatusAberta();
        Integer partidaId = partida.getId();
        usuarioPartidaService.cancelarPresenca(usuario, partidaId);
        return ResponseEntity.noContent().build();
    }
}
