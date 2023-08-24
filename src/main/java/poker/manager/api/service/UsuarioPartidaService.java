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

    public UsuarioPartida confirmarPresenca(Partida partida, Usuario usuario) {
        usuario = usuarioRepository.getReferenceById(usuario.getId());
        partida = partidaRepository.getReferenceById(partida.getId());
        UsuarioPartida usuarioPartida = new UsuarioPartida();
        usuarioPartida.getId().setPartida(partida);
        usuarioPartida.getId().setUsuario(usuario);
        if(usuarioPartida.getId().getUsuario().getId() == partida.getUsuarioAnfitriaoId()){
            usuarioPartida.setAnfitriao(true);
        }
        usuarioPartidaRepository.save(usuarioPartida);
        usuarioRepository.save(usuario);
        partidaRepository.save(partida);
        return usuarioPartida;
    }

    public UsuarioPartida cancelarPresenca(Usuario usario, Integer partidaId) {
        Partida partida = partidaService.buscarPartida(partidaId);
        Usuario usuarioCancelando = usuarioRepository.getReferenceById(usario.getId());
        UsuarioPartida usuarioPartida = usuarioPartidaRepository.findFirstByIdUsuarioAndIdPartida(usario.getId(),partida.getId());
        if (partida.getUsuarioAnfitriaoId() == usuarioPartida.getId().getUsuario().getId()) {
            partidaService.anfitriaoCancelado(partida);
        }
        usuarioPartida.setCancelado(true);
        usuarioPartidaRepository.save(usuarioPartida);
        return usuarioPartida;
    }

}
