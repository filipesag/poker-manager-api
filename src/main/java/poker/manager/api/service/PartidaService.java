package poker.manager.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.domain.enums.PartidaStatus;
import poker.manager.api.dto.PartidaDTO;
import poker.manager.api.dto.UsuarioDTO;
import poker.manager.api.repository.PartidaRepository;
import poker.manager.api.repository.UsuarioPartidaRepository;
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
    @Autowired
    private UsuarioPartidaRepository usuarioPartidaRepository;



    public PartidaDTO buscarPartida(Integer id) {
        Optional<Partida> partida = partidaRepository.findById(id);
        Optional<PartidaDTO> partidaBUscada = partida.map(PartidaDTO::new);
        return partidaBUscada.get();

    }

    public PartidaDTO criarPartida(PartidaDTO partidaDTO) {
        Partida partidaNova = new Partida(partidaDTO.id(),null,null,
                partidaDTO.data(),partidaDTO.status());
        partidaNova =  partidaRepository.save(partidaNova);
        return partidaDTO;
    }

    public PartidaDTO cadastrarAnfitriao(PartidaDTO partidaDTO, UsuarioDTO anfitriaoDTO) {
        Partida partida = partidaRepository.getReferenceById(partidaDTO.id());
        Usuario anfitriao = usuarioRepository.getReferenceById(partidaDTO.usuarioAnfitriaoId());
        partida.setUsuarioAnfitriaoId(partidaDTO.usuarioAnfitriaoId());
        partida.setStatus(PartidaStatus.ABERTA);
        partida.setQuantidadeJogadores(partidaDTO.quantidadeJogadores());
        UsuarioPartida usuarioPartida = usuarioPartidaService.confirmarPresenca(partidaDTO, anfitriaoDTO);
        usuarioPartidaRepository.save(usuarioPartida);
        partidaRepository.save(partida);
        usuarioRepository.save(anfitriao);
        return partidaDTO;
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
