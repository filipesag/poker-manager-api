package poker.manager.api.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.domain.enums.PartidaStatus;
import poker.manager.api.dto.PartidaDTO;
import poker.manager.api.repository.PartidaRepository;
import poker.manager.api.repository.UsuarioPartidaRepository;
import poker.manager.api.repository.UsuarioRepository;
import poker.manager.api.domain.Partida;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioPartidaService usuarioPartidaService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioPartidaRepository usuarioPartidaRepository;



    public Partida buscarPartida(Integer id) {
        Optional<Partida> partida = partidaRepository.findById(id);
        return partida.get();

    }

    public Partida criarPartida(Partida partida) {
        partida.setStatus(PartidaStatus.AGUARDANDO_ANFITRIAO);
        partida.setBucketPorPessoa(partida.getBucketPorPessoa());
        partida = partidaRepository.save(partida);
        return partida;
    }

    public void cadastrarAnfitriao(Partida partida) {
        Partida partidaASerCriada = new Partida();
        partidaASerCriada = partidaRepository.getReferenceById(partida.getId());
        partidaASerCriada.setUsuarioAnfitriaoId(partida.getUsuarioAnfitriaoId());
        partidaASerCriada.setStatus(PartidaStatus.ABERTA);
        partidaASerCriada.setQuantidadeJogadores(partida.getQuantidadeJogadores());
        Usuario anfitriao = usuarioRepository.findById(partidaASerCriada.getUsuarioAnfitriaoId()).get();
        UsuarioPartida usuarioPartida = new UsuarioPartida();
        usuarioPartida.setUsuario(anfitriao);
        partidaRepository.save(partidaASerCriada);
        usuarioPartida = usuarioPartidaService.confirmarPresenca(partidaASerCriada, anfitriao);
        usuarioPartidaRepository.save(usuarioPartida);
    }

    public void anfitriaoCancelado(Partida partida) {
        partida = partidaRepository.getReferenceById(partida.getId());
        partida.setUsuarioAnfitriaoId(null);
        partida.setStatus(PartidaStatus.AGUARDANDO_ANFITRIAO);
        partida.setQuantidadeJogadores(null);
        partidaRepository.save(partida);
    }

    public void iniciarPartida(Partida partida) {
        partida = partidaRepository.getReferenceById(partida.getId());
        partida.setStatus(PartidaStatus.INICIADA);
        partidaRepository.save(partida);
    }

    public void cancelarPartida(Partida partida) {
        partida = partidaRepository.getReferenceById(partida.getId());
        Set<UsuarioPartida> players = usuarioPartidaRepository.findAllPlayersByMatch(partida.getId());
        for(UsuarioPartida player:players){
            player.setCancelado(true);
            player.setAnfitriao(false);
        }
        partida.setStatus(PartidaStatus.CANCELADA);
        partidaRepository.save(partida);
    }

    public Partida buscarPorStatusAberta(){
       Partida partida = partidaRepository.findByStatusAberta();
        return partida;
    }

    public Partida findByStatusInciada() {
        Partida partida = partidaRepository.findByStatusInciada();
        return partida;
    }

    public Usuario obterEndereco(PartidaDTO partidaDTO){
        Integer id = partidaDTO.usuarioAnfitriaoId();
        Usuario anfitriao = usuarioRepository.getReferenceById(id);
        return anfitriao;
    }

    public void finalizarPartida(Partida partida) {
        partida.setStatus(PartidaStatus.FINALIZADA);
        partidaRepository.save(partida);
    }
}
