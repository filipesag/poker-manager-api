package poker.manager.api.rest;

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
    public ResponseEntity<PartidaDTO> buscarPartida(@PathVariable Integer id) {
        PartidaDTO partida = partidaService.buscarPartida(id);
        return ResponseEntity.ok().body(partida);
    }

    @PostMapping(value = "/creation")
    public ResponseEntity<PartidaDTO> criarNovaPartida(@RequestBody @DateTimeFormat(pattern = "dd/MM/yyyy") PartidaDTO partidaDTO) {
        var partida = partidaService.criarPartida(partidaDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(partida.id()).toUri();
        return ResponseEntity.created(uri).body(partida);
    }

    @PostMapping(value = "/host")
    public ResponseEntity<PartidaDTO> cadastrarAnfitriao(@RequestBody PartidaDTO partidaDTO, UsuarioDTO anfitriaoDTO) {
        System.out.println(partidaDTO.toString() + "\n" +anfitriaoDTO.toString());
        var partida = partidaService.cadastrarAnfitriao(partidaDTO, anfitriaoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(partida.id()).toUri();
        return ResponseEntity.created(uri).body(partida);
    }

    @PutMapping(value = "/start-match")
    public ResponseEntity<Partida> iniciarPartida(@RequestBody PartidaDTO partida){
        partidaService.iniciarPartida(partida);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/calloff-match")
    public ResponseEntity<Partida> cancelarPartida(@RequestBody PartidaDTO partida) {
        partidaService.cancelarPartida(partida);
        return ResponseEntity.noContent().build();
    }
}
