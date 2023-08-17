package poker.manager.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poker.manager.api.domain.Partida;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.dto.PartidaDTO;
import poker.manager.api.dto.UsuarioDTO;
import poker.manager.api.repository.UsuarioPartidaRepository;

import java.util.List;
@Service
public class UsuarioPartidaService {
    @Autowired
    private UsuarioPartidaRepository usuarioPartidaRepository;
    @Autowired
    private  UsuarioService usuarioService;
    @Autowired
    private  PartidaService partidaService;



    public List<UsuarioPartida> buscarTudo(){
        return usuarioPartidaRepository.findAll();
    }

    public UsuarioPartida confirmarPresenca(PartidaDTO partidaDTO, UsuarioDTO usuarioDTO) {
        Usuario user = usuarioService.buscarUsuario(usuarioDTO.id());
        UsuarioPartida usuarioPartida = new UsuarioPartida();
        Partida partida = partidaService.buscarPartida(partidaDTO.id());
        usuarioPartida.getId().setUsuario(user);
        usuarioPartida.getId().setPartida(partida);
        usuarioPartidaRepository.save(usuarioPartida);
        return usuarioPartida;
    }

    public UsuarioPartida cancelarPresenca(UsuarioDTO usarioDTO, PartidaDTO partidaDTO) {
        Partida partida = partidaService.buscarPartida(partidaDTO.id());
        Usuario usuarioCancelando = usuarioService.buscarUsuario(usarioDTO.id());
        UsuarioPartida usuarioPartida = usuarioPartidaRepository.findFirstByIdUsuarioAndIdPartida(usuarioCancelando.getId(),partida.getId());
        if (partida.getUsuarioAnfitriaoId() == usuarioPartida.getId().getUsuario().getId()) {
            partidaService.anfitriaoCancelado(partidaDTO);
        }
        usuarioPartida.setCancelado(true);
        usuarioPartidaRepository.save(usuarioPartida);
        return usuarioPartida;
    }

}
