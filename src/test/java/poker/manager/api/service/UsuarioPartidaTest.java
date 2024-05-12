package poker.manager.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import poker.manager.api.domain.Partida;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.domain.enums.UserRole;
import poker.manager.api.dto.PartidaDTO;
import poker.manager.api.dto.UsuarioDTO;
import poker.manager.api.repository.PartidaRepository;
import poker.manager.api.repository.UsuarioPartidaRepository;
import poker.manager.api.service.UsuarioPartidaService;
import poker.manager.api.service.exceptions.PartidaFullException;
import java.time.LocalDate;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UsuarioPartidaTest {

    @Mock
    private UsuarioPartidaRepository repository;

    @Mock
    private PartidaRepository partidaRepository;

    @InjectMocks
    private UsuarioPartidaService service;

    private Partida partida;

    private UsuarioDTO usuarioDTO;

    private UsuarioPartida usuarioPartida;

    private PartidaDTO partidaDTO;

    private Usuario usuario;

    private Set<UsuarioPartida> setUsuarioPartida;

    @BeforeEach
    public void setup() {
        partidaDTO = new PartidaDTO(1,50.0,4,5, LocalDate.of(2024,06,20), null,setUsuarioPartida);
        usuarioDTO = new UsuarioDTO(1, "User Test", "userintest", "pixTest@gmail.com", "Rua Test 123", UserRole.USER, true, null);
        usuario = new Usuario(usuarioDTO);
        partida = new Partida(partidaDTO);
        usuarioPartida = new UsuarioPartida(partida, usuario);
    }


    @Test
    @DisplayName("Testando confirmação de presença em partida com sucesso")
    public void testConfirmAttendanceInMatchSuccessfuly() {
        given(repository.save(any())).willReturn(usuarioPartida);
        service.confirmarPresenca(partida, usuario);

        verify(repository).save(any(UsuarioPartida.class));
        assertNotNull(usuarioPartida);
        assertEquals(false, usuarioPartida.getRebuy());
        assertEquals(false, usuarioPartida.getCancelado());
        assertEquals(0, usuarioPartida.getFichasFinal());
        assertEquals(0.0, usuarioPartida.getNetProFit());
        assertEquals(0, usuarioPartida.getColocacao());
        assertEquals(false, usuarioPartida.getAnfitriao());
    }

    @Test
    @DisplayName("Testando exceção lançada de partida lotada")
    public void testExceptionThrownWhenMatchIsFull() {
        UsuarioPartida usuarioPartida2 = new UsuarioPartida(partida, usuario);
        UsuarioPartida usuarioPartida3 = new UsuarioPartida(partida, usuario);
        UsuarioPartida usuarioPartida4 = new UsuarioPartida(partida, usuario);
        UsuarioPartida usuarioPartida5 = new UsuarioPartida(partida, usuario);

        given(service.obterJogadoresDePartida(any())).willReturn(Set.of(usuarioPartida, usuarioPartida2, usuarioPartida3, usuarioPartida4, usuarioPartida5));
        partida.setQuantidadeJogadores(5);

        assertThrows(PartidaFullException.class, () -> {
            service.confirmarPresenca(partida, usuario);
        });
        verify(repository, never()).save(any(UsuarioPartida.class));
    }

    @Test
    @DisplayName("Testando cancelar presença")
    public void testCallOffAtenddanceInMatch() {
        given(repository.buscaPorIdUsuarioAndIdPartida(partida.getId(), usuario.getId())).willReturn(usuarioPartida);
        given(repository.save(usuarioPartida)).willReturn(usuarioPartida);

        service.cancelarPresenca(partida, usuario);

        verify(repository).save(usuarioPartida);
        assertEquals(true, usuarioPartida.getCancelado());
        assertEquals(false, usuarioPartida.getAnfitriao());
    }

    @Test
    @DisplayName("Testando obter jogadores de partida")
    public void testGettingPlayersFromMatch() {
        UsuarioDTO usuarioDTO2 = new UsuarioDTO(2, "User Test Two", "userintest2", "pix2Test@gmail.com", "Rua Test 123", UserRole.USER, true, null);
        Usuario usuario2 = new Usuario(usuarioDTO2);
        UsuarioDTO usuarioDTO3 = new UsuarioDTO(3, "User Test Three", "userintest2", "pix3Test@gmail.com", "Rua Test 123", UserRole.USER, true, null);
        Usuario usuario3 = new Usuario(usuarioDTO3);
        UsuarioPartida usuarioPartida2 = new UsuarioPartida(partida, usuario2);
        UsuarioPartida usuarioPartida3 = new UsuarioPartida(partida, usuario3);
        given(repository.buscaTodosJogadoresPorPartida(partida.getId())).willReturn(Set.of(usuarioPartida, usuarioPartida2, usuarioPartida3));

        Set<UsuarioPartida> players = service.obterJogadoresDePartida(partida.getId());

        assertNotNull(players);
        assertEquals(3, players.size());
    }

    @Test
    @DisplayName("Testando calculo de fichas finais")
    public void testGettingTotalOfCoins() {
        UsuarioDTO usuarioDTO2 = new UsuarioDTO(2, "User Test Two", "userintest2", "pix2Test@gmail.com", "Rua Test 123", UserRole.USER, true, null);
        Usuario usuario2 = new Usuario(usuarioDTO2);
        UsuarioDTO usuarioDTO3 = new UsuarioDTO(3, "User Test Three", "userintest2", "pix3Test@gmail.com", "Rua Test 123", UserRole.USER, true, null);
        Usuario usuario3 = new Usuario(usuarioDTO3);
        UsuarioPartida usuarioPartida2 = new UsuarioPartida(partida, usuario2);
        UsuarioPartida usuarioPartida3 = new UsuarioPartida(partida, usuario3);
        usuarioPartida.setFichasFinal(1000);
        usuarioPartida2.setFichasFinal(700);
        usuarioPartida3.setFichasFinal(780);
        given(repository.buscaTodosJogadoresPorPartida(partida.getId())).willReturn(Set.of(usuarioPartida, usuarioPartida2, usuarioPartida3));

        Integer totalOfCoins = service.calculaFichasTotais(partida.getId());

        assertNotNull(totalOfCoins);
        assertEquals(2480, totalOfCoins);
    }

    @Test
    @DisplayName("Testando calculo de fichas finais sem rebuy")
    public void testGettingTotalOfBucketValueWithNoRebuy() {
        UsuarioDTO usuarioDTO2 = new UsuarioDTO(2, "User Test Two", "userintest2", "pix2Test@gmail.com", "Rua Test 123", UserRole.USER, true, null);
        Usuario usuario2 = new Usuario(usuarioDTO2);
        UsuarioDTO usuarioDTO3 = new UsuarioDTO(3, "User Test Three", "userintest2", "pix3Test@gmail.com", "Rua Test 123", UserRole.USER, true, null);
        Usuario usuario3 = new Usuario(usuarioDTO3);
        UsuarioPartida usuarioPartida2 = new UsuarioPartida(partida, usuario2);
        UsuarioPartida usuarioPartida3 = new UsuarioPartida(partida, usuario3);

        given(repository.buscaTodosJogadoresPorPartida(partida.getId())).willReturn(Set.of(usuarioPartida, usuarioPartida2, usuarioPartida3));
        given(partidaRepository.getReferenceById(partida.getId())).willReturn(partida);

        Double totalBucketValue = service.calculaValorDoPote(partida.getId());

        assertNotNull(totalBucketValue);
        assertEquals(150.0, totalBucketValue);
    }

    @Test
    @DisplayName("Testando calculo de fichas finais com rebuy")
    public void testGettingTotalOfBucketValueWithRebuy() {
        UsuarioDTO usuarioDTO2 = new UsuarioDTO(2, "User Test Two", "userintest2", "pix2Test@gmail.com", "Rua Test 123", UserRole.USER, true, null);
        Usuario usuario2 = new Usuario(usuarioDTO2);
        UsuarioDTO usuarioDTO3 = new UsuarioDTO(3, "User Test Three", "userintest2", "pix3Test@gmail.com", "Rua Test 123", UserRole.USER, true, null);
        Usuario usuario3 = new Usuario(usuarioDTO3);
        UsuarioPartida usuarioPartida2 = new UsuarioPartida(partida, usuario2);
        UsuarioPartida usuarioPartida3 = new UsuarioPartida(partida, usuario3);
        usuarioPartida3.setRebuy(true);

        given(repository.buscaTodosJogadoresPorPartida(partida.getId())).willReturn(Set.of(usuarioPartida, usuarioPartida2, usuarioPartida3));
        given(partidaRepository.getReferenceById(partida.getId())).willReturn(partida);

        Double totalBucketValue = service.calculaValorDoPote(partida.getId());

        assertNotNull(totalBucketValue);
        assertEquals(200.0, totalBucketValue);
    }

    @Test
    @DisplayName("Testando inserir dados finais da partida")
    public void testInsertFinalDataFromMatch() {
        given(repository.save(usuarioPartida)).willReturn(usuarioPartida);

        service.inserirDadosFimDaPartida(usuarioPartida);

        assertNotNull(usuarioPartida);
        verify(repository).save(usuarioPartida);
    }

}
