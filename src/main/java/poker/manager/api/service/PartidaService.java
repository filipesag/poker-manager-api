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

import java.util.Optional;

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
        partida =  partidaRepository.save(partida);
        return partida;
    }

    public void cadastrarAnfitriao(Partida partida) {
        Partida partidaASerCriada = new Partida();
        partidaASerCriada = partidaRepository.getReferenceById(partida.getId());
        partidaASerCriada.setUsuarioAnfitriaoId(partida.getUsuarioAnfitriaoId());
        partidaASerCriada.setStatus(PartidaStatus.ABERTA);
        partidaASerCriada.setQuantidadeJogadores(partida.getQuantidadeJogadores());
        partidaRepository.save(partidaASerCriada);
        Usuario anfitriao = usuarioRepository.findById(partidaASerCriada.getUsuarioAnfitriaoId()).get();
        UsuarioPartida usuarioPartida = usuarioPartidaService.confirmarPresenca(partida, anfitriao);
        usuarioPartidaRepository.save(usuarioPartida);
    }

    public void anfitriaoCancelado(Partida partida) {
        Partida partidaAnfitriaoCancelando = partidaRepository.getReferenceById(partida.getId());
        partida.setUsuarioAnfitriaoId(null);
        partida.setStatus(PartidaStatus.ABERTA);
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
        partida.setStatus(PartidaStatus.CANCELADA);
        partidaRepository.save(partida);
    }

    public String obterEndereco(PartidaDTO partidaDTO){
        Integer id = partidaDTO.usuarioAnfitriaoId();
        Usuario anfitriao = usuarioRepository.getReferenceById(id);
        return anfitriao.getEndereco();
    }

}
