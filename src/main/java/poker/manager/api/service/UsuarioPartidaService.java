package poker.manager.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poker.manager.api.domain.Partida;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.dto.PartidaDTO;
import poker.manager.api.dto.UsuarioPartidaDTO;
import poker.manager.api.repository.PartidaRepository;
import poker.manager.api.repository.UsuarioPartidaRepository;
import poker.manager.api.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioPartidaService {
    @Autowired
    private UsuarioPartidaRepository usuarioPartidaRepository;
    @Autowired
    private  UsuarioService usuarioService;
    @Autowired
    private  PartidaService partidaService;

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioPartida confirmarPresenca(Partida partida, Usuario usuario) {
        usuario = usuarioRepository.getReferenceById(usuario.getId());
        partida = partidaRepository.getReferenceById(partida.getId());
        UsuarioPartida usuarioPartida = new UsuarioPartida();
        usuarioPartida.setPartida(partida);
        usuarioPartida.setUsuario(usuario);
        if(usuarioPartida.getUsuario().getId().equals(partida.getUsuarioAnfitriaoId())){
            usuarioPartida.setAnfitriao(true);
        }
        usuarioPartidaRepository.save(usuarioPartida);
        usuarioRepository.save(usuario);
        partidaRepository.save(partida);
        return usuarioPartida;
    }

    public void cancelarPresenca(Usuario usuario, Integer partidaId) {
        Partida partida = partidaRepository.getReferenceById(partidaId);
        UsuarioPartida usuarioPartida = usuarioPartidaRepository.findByIdUsuarioAndIdPartida(partida.getId(),usuario.getId());
        if (partida.getUsuarioAnfitriaoId() == usuarioPartida.getUsuario().getId()) {
            partidaService.anfitriaoCancelado(partida);
            usuarioPartida.setAnfitriao(false);
        }
        usuarioPartida.setCancelado(true);
        usuarioPartidaRepository.save(usuarioPartida);
    }

    public Set<UsuarioPartida> obterJogadoresDePartida(Integer partidaId) {
        Set<UsuarioPartida> jogadores = usuarioPartidaRepository.findAllPlayersByMatch(partidaId);
        return jogadores;
    }

    public Integer calculaFichasTotais(Integer id) {
        Set<UsuarioPartida> partida = usuarioPartidaRepository.findAllPlayersByMatch(id);
        Integer fichasTotais = partida.stream().mapToInt(player -> player.getFichasFinal()).sum();
        return fichasTotais;
    }

    public Double calculaValorDoPote(Integer id) {
        Partida partida = partidaRepository.getReferenceById(id);
        Double bucketPrice = partida.getBucketPorPessoa();
        Double totalBucketWithNoRebuy = usuarioPartidaRepository.findAllPlayersByMatch(id).size() * bucketPrice;
        Set<UsuarioPartida> players = usuarioPartidaRepository.findAllPlayersByMatch(id);
        Long rebuys = players.stream().filter(x -> x.getRebuy() == true).count();
        Double finalTotalBucket = totalBucketWithNoRebuy + (rebuys * bucketPrice);
        return finalTotalBucket;
    }

    public void inserirDadosFimDaPartida(UsuarioPartida player) {
        usuarioPartidaRepository.save(player);
    }

    public Set<UsuarioPartida> findByIdPartida(Integer id) {
        Set<UsuarioPartida> players = usuarioPartidaRepository.findByIdPartida(id);
        return players;
    }
}
