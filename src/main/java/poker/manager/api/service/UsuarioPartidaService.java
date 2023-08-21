package poker.manager.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import poker.manager.api.domain.Partida;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.dto.PartidaDTO;
import poker.manager.api.dto.UsuarioDTO;
import poker.manager.api.repository.PartidaRepository;
import poker.manager.api.repository.UsuarioPartidaRepository;
import poker.manager.api.repository.UsuarioRepository;

import java.util.List;
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



    public List<UsuarioPartida> buscarTudo(){
        return usuarioPartidaRepository.findAll();
    }

    public UsuarioPartida confirmarPresenca(PartidaDTO partidaDTO, UsuarioDTO usuarioDTO) {
        Usuario user = new Usuario();
        user.setId(partidaDTO.id());
        Partida partida = new Partida();
        partida.setId(usuarioDTO.id());
        UsuarioPartida usuarioPartida = new UsuarioPartida();
        usuarioPartida.getId().setUsuario(user);
        usuarioPartida.getId().setPartida(partida);
        usuarioPartidaRepository.save(usuarioPartida);
        usuarioRepository.save(user);
        partidaRepository.save(partida);
        return usuarioPartida;
    }

    public UsuarioPartida cancelarPresenca(UsuarioDTO usarioDTO, Integer partidaId) {
        PartidaDTO partida = partidaService.buscarPartida(partidaId);
        Usuario user = new Usuario();
        user.setId(usarioDTO.id());
        UsuarioPartida usuarioPartida = usuarioPartidaRepository.findFirstByIdUsuarioAndIdPartida(user.getId(),partida.id());
        if (partida.usuarioAnfitriaoId() == usuarioPartida.getId().getUsuario().getId()) {
            partidaService.anfitriaoCancelado(partida);
        }
        usuarioPartida.setCancelado(true);
        usuarioPartidaRepository.save(usuarioPartida);
        return usuarioPartida;
    }

}
