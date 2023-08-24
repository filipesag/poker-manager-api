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
import poker.manager.api.dto.PartidaDTO;
import poker.manager.api.dto.UsuarioDTO;
import poker.manager.api.service.PartidaService;

import java.net.URI;
@RestController
@RequestMapping("/match")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;


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

    @Transactional
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
}
