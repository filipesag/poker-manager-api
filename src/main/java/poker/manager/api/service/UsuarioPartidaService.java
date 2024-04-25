package poker.manager.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poker.manager.api.domain.Partida;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.repository.PartidaRepository;
import poker.manager.api.repository.UsuarioPartidaRepository;
import poker.manager.api.repository.UsuarioRepository;
import poker.manager.api.service.exceptions.PartidaFullException;
import poker.manager.api.service.exceptions.PartidaWithNoHostException;
import poker.manager.api.service.exceptions.UsuarioAlreadyInMatchException;

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

    public void confirmarPresenca(Partida partida, Usuario usuario) {
        if (isPartidaLotada(partida)) {
            throw new PartidaFullException();
        }
        if (isUsuarioJaConfirmado(partida, usuario)) {
            throw new UsuarioAlreadyInMatchException();
        }

        UsuarioPartida usuarioPartida = new UsuarioPartida(partida, usuario);
        usuarioPartida.setRebuy(false);
        usuarioPartida.setFichasFinal(0);
        usuarioPartida.setNetProFit(0.0);
        usuarioPartida.setColocacao(0);
        usuarioPartida.setCancelado(false);
        usuarioPartida.setAnfitriao(usuario.getId().equals(partida.getUsuarioAnfitriaoId()));
        usuarioPartidaRepository.save(usuarioPartida);
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

    public Boolean isPartidaLotada(Partida partida) {
        Integer quantidadeDeJogadores = partida.getQuantidadeJogadores();
        Integer numeroDeJogadaoresNaMesa = obterJogadoresDePartida(partida.getId()).size();
        if (numeroDeJogadaoresNaMesa < quantidadeDeJogadores) {
            return false;
        }else {
            return true;
        }
    }

    public Boolean isUsuarioJaConfirmado(Partida partida, Usuario usuario) {
        boolean isInscrito = false;
        Set<UsuarioPartida> jogadores = partida.getJogadores();
        for (UsuarioPartida x : jogadores) {
            if(x.getUsuario().getId().equals(usuario.getId())) {
                isInscrito = true;
            }
        }
        return isInscrito;
    }
}
