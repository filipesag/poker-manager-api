package poker.manager.api.rest;

import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poker.manager.api.domain.Partida;
import poker.manager.api.domain.Usuario;
import poker.manager.api.dto.PartidaDTO;
import poker.manager.api.dto.UsuarioDTO;
import poker.manager.api.dto.UsuarioPartidaDTO;
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
    public ResponseEntity<UsuarioPartida> calculaGanhos() {
        Partida partida = partidaService.buscaPorStatusFinalizada();
        Integer fichasTotais = usuarioPartidaService.calculaFichasTotais(partida.getId());
        Double valorDoPote = usuarioPartidaService.calculaValorDoPote(partida.getId());
        Set<UsuarioPartida> players = partida.getJogadores();
        List<UsuarioPartida> sortedPlayers = new ArrayList<>(players);
        sortedPlayers.sort(Comparator.comparingDouble(UsuarioPartida::getNetProFit).reversed());
        Double netProFit;
        for(UsuarioPartida x : players) {
            Double valorPorUnidade = valorDoPote/fichasTotais;
            Double profit = valorPorUnidade * x.getFichasFinal();
            if(x.getRebuy() == true) {
                netProFit = profit - (partida.getBucketPorPessoa() * 2);
            }else {
                netProFit = profit - (partida.getBucketPorPessoa());
            }
            x.setNetProFit(Math.round(netProFit * 100.0) / 100.0);
            usuarioPartidaService.inserirDadosFimDaPartida(x);
        }
        int colocacao = 1;
        for (UsuarioPartida player : sortedPlayers) {
            player.setColocacao(colocacao++);
            usuarioPartidaService.inserirDadosFimDaPartida(player);
        }
        return ResponseEntity.noContent().build();
    }


}


















