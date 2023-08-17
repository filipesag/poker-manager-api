package poker.manager.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.enums.PartidaStatus;
import poker.manager.api.dto.PartidaDTO;
import poker.manager.api.dto.UsuarioDTO;
import poker.manager.api.repository.PartidaRepository;
import poker.manager.api.repository.UsuarioRepository;
import poker.manager.api.domain.Partida;

import java.util.Optional;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioPartidaService usuarioPartidaService;
    @Autowired
    private UsuarioRepository usuarioRepository;


    public Partida buscarPartida(Integer id) {
        Optional<Partida> partida = partidaRepository.findById(id);
        return partida.get();
    }

    public Partida criarPartida(PartidaDTO partidaDTO) {
        Partida partidaNova = new Partida();
        partidaNova.setData(partidaDTO.data());
        partidaNova.setStatus(PartidaStatus.AGUARDANDO_ANFITRIAO);
        partidaRepository.save(partidaNova);
        return partidaNova;
    }

    public Partida cadastrarAnfitriao(UsuarioDTO anfitriaoDTO, PartidaDTO partidaDTO) {
        Partida partida = partidaRepository.getReferenceById(partidaDTO.id());
        partida.setUsuarioAnfitriaoId(partidaDTO.usuarioAnfitriaoId());
        partida.setStatus(PartidaStatus.ABERTA);
        partida.setQuantidadeJogadores(partidaDTO.quantidadeJogadores());
        usuarioPartidaService.confirmarPresenca(partidaDTO, anfitriaoDTO);
        partidaRepository.save(partida);
        return partida;
    }

    public void anfitriaoCancelado(PartidaDTO partidaDTO) {
        Partida partida = partidaRepository.getReferenceById(partidaDTO.id());
        partida.setUsuarioAnfitriaoId(null);
        partida.setStatus(PartidaStatus.ABERTA);
        partida.setQuantidadeJogadores(null);
        partidaRepository.save(partida);
    }

    public void iniciarPartida(PartidaDTO partidaDTO) {
        Partida partida = partidaRepository.getReferenceById(partidaDTO.id());
        partida.setStatus(PartidaStatus.INICIADA);
        partidaRepository.save(partida);
    }

    public void cancelarPartida(PartidaDTO partidaDTO) {
        Partida  partida = partidaRepository.getReferenceById(partidaDTO.id());
        partida.setStatus(PartidaStatus.CANCELADA);
        partidaRepository.save(partida);
    }

    public String obterEndereco(PartidaDTO partidaDTO){
        Integer id = partidaDTO.usuarioAnfitriaoId();
        Usuario anfitriao = usuarioRepository.getReferenceById(id);
        return anfitriao.getEndereco();
    }

}
