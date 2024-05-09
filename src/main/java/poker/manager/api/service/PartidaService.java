package poker.manager.api.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.domain.enums.PartidaStatus;
import poker.manager.api.dto.PartidaDTO;
import poker.manager.api.repository.PartidaRepository;
import poker.manager.api.repository.UsuarioPartidaRepository;
import poker.manager.api.repository.UsuarioRepository;
import poker.manager.api.domain.Partida;
import poker.manager.api.service.exceptions.PartidaStatusRepeatedException;
import poker.manager.api.service.exceptions.PartidaUnableToUpdateException;
import poker.manager.api.service.exceptions.PartidaWithNoHostException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;
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
        if(partida.getStatus() != PartidaStatus.AGUARDANDO_ANFITRIAO) {
            throw new PartidaUnableToUpdateException();
        }
        Partida partidaAtual = partidaRepository.getReferenceById(partida.getId());
        partidaAtual.setStatus(PartidaStatus.ABERTA);
        partidaAtual.setBucketPorPessoa(partida.getBucketPorPessoa());
        partidaAtual.setQuantidadeJogadores(partida.getQuantidadeJogadores());
        partidaAtual.setUsuarioAnfitriaoId(partida.getUsuarioAnfitriaoId());
        partidaRepository.save(partidaAtual);
        Usuario usuario = usuarioRepository.getReferenceById(partidaAtual.getUsuarioAnfitriaoId());
        usuarioPartidaService.confirmarPresenca(partidaAtual, usuario);
    }

    public void anfitriaoCancelado(Partida partida) {
        partida.setStatus(PartidaStatus.CANCELADA);
        partidaRepository.save(partida);
    }

    public void iniciarPartida(Partida partida) {
        partida = partidaRepository.getReferenceById(partida.getId());
        partida.setStatus(PartidaStatus.INICIADA);
        partidaRepository.save(partida);
    }

    public void cancelarPartida(Partida partida) {
        partida = partidaRepository.getReferenceById(partida.getId());
        Set<UsuarioPartida> players = usuarioPartidaRepository.buscaTodosJogadoresPorPartida(partida.getId());
        for(UsuarioPartida player:players){
            player.setCancelado(true);
            player.setAnfitriao(false);
            usuarioPartidaRepository.save(player);
        }
        partida.setStatus(PartidaStatus.CANCELADA);
        partidaRepository.save(partida);

    }

    public Boolean isAnfitriaoDefinido() {
        Partida partida  = partidaRepository.buscaPorStatusAguardandoAnfitriao();
        if(partida == null) {
            return true;
        } else{
            return false;
        }
    }

    public Partida buscarPorStatusAberta(){
        if(!isAnfitriaoDefinido()) {
            throw new PartidaWithNoHostException();
        }
        List<Partida> partida = partidaRepository.buscaPorStatusAberta();
        if(partida.size() > 1) {
            throw new PartidaStatusRepeatedException();
        }
        return partida.get(0);
    }

    public Partida buscaPorStatusIniciada() {
        Partida partida = partidaRepository.buscaPorStatusInciada();
        if(partida == null) {
            throw new PartidaStatusRepeatedException();
        }
        return partida;
    }

    public Usuario obterEndereco(PartidaDTO partidaDTO){
        Integer id = partidaDTO.usuarioAnfitriaoId();
        Usuario anfitriao = usuarioRepository.getReferenceById(id);
        return anfitriao;
    }

    public void finalizarPartida(Partida partida) {
        if(partida.getStatus() == PartidaStatus.CANCELADA || partida.getStatus() == PartidaStatus.FECHADA) {
            throw new PartidaUnableToUpdateException();
        }
        partida.setStatus(PartidaStatus.FINALIZADA);
        partidaRepository.save(partida);
    }

    public void fecharPartida(Partida partida) {
        if(partida.getStatus() == PartidaStatus.CANCELADA || partida.getStatus() == PartidaStatus.FECHADA) {
            throw new PartidaUnableToUpdateException();
        }
        partida.setStatus(PartidaStatus.FECHADA);
        partidaRepository.save(partida);
    }

    public Partida buscaPorStatusFinalizada() {
        Partida partida = partidaRepository.buscaPorStatusFinalizada();
        if(partida == null) {
            throw new PartidaStatusRepeatedException();
        }
        return partida;
    }
}
