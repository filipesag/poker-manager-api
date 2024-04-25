package poker.manager.api.rest;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import poker.manager.api.domain.Partida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import poker.manager.api.domain.Usuario;
import poker.manager.api.dto.PartidaDTO;
import poker.manager.api.service.PartidaService;
import poker.manager.api.service.UsuarioPartidaService;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/match")
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


    @PostMapping(value = "/creation")
    public ResponseEntity<Partida> criarNovaPartida(@RequestBody PartidaDTO partidaDTO) {
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

    @GetMapping(value = "/host-adress/{id}")
    public ResponseEntity<String> obterEndereco(@PathVariable Integer id) {
        Partida partida = partidaService.buscarPartida(id);
        PartidaDTO partidaDto = new PartidaDTO(partida);
        Usuario enderecoUsuario = partidaService.obterEndereco(partidaDto);
        return ResponseEntity.ok().body(enderecoUsuario.getEndereco());
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


}
