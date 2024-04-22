package poker.manager.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poker.manager.api.domain.Partida;
import poker.manager.api.domain.Usuario;
import poker.manager.api.dto.UsuarioDTO;
import poker.manager.api.service.PartidaService;
import poker.manager.api.service.UsuarioPartidaService;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.service.UsuarioService;

import java.util.*;


@RestController
@RequestMapping("/api/v1/match_users")
public class UsuarioPartidaController {
    @Autowired
    private UsuarioPartidaService usuarioPartidaService;

    @Autowired
    private PartidaService partidaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = "/find-all-in-match/{id}")
    public ResponseEntity<Set<UsuarioPartida>>buscarJogadoresDeUmaPartida(@PathVariable Integer id){
        Partida partida = partidaService.buscarPartida(id);
        Set<UsuarioPartida> usuarioPartida = usuarioPartidaService.obterJogadoresDePartida(id);
        return ResponseEntity.ok().body(usuarioPartida);
    }

    @PostMapping(value = "/user/confirmation")
    public ResponseEntity<Partida> confirmarPresenca(@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario(usuarioDTO);
        Partida partida = partidaService.buscarPorStatusAberta();
        Integer quantidadeDeJogadores = partida.getQuantidadeJogadores();
        Integer numeroDeJogadaoresNaMesa = usuarioPartidaService.obterJogadoresDePartida(partida.getId()).size();
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

    @PutMapping(value = "/caculate-profit")
    public ResponseEntity<UsuarioPartida> calculaGanhos(@PathVariable Integer id) {
        Partida partida = partidaService.buscarPartida(id);
        Integer fichasTotais = usuarioPartidaService.calculaFichasTotais(id);
        Integer jogadoresDaPartida = usuarioPartidaService.obterJogadoresDePartida(id).size();
        Double valorDoPote = usuarioPartidaService.calculaValorDoPote(id);
        return null;
    }

}


















