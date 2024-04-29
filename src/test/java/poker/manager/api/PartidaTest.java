package poker.manager.api;

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
import poker.manager.api.domain.enums.PartidaStatus;
import poker.manager.api.domain.enums.UserRole;
import poker.manager.api.dto.PartidaDTO;
import poker.manager.api.dto.UsuarioDTO;
import poker.manager.api.repository.PartidaRepository;
import poker.manager.api.repository.UsuarioPartidaRepository;
import poker.manager.api.repository.UsuarioRepository;
import poker.manager.api.service.PartidaService;
import poker.manager.api.service.UsuarioPartidaService;
import poker.manager.api.service.exceptions.PartidaUnableToUpdateException;
import poker.manager.api.service.exceptions.PartidaWithNoHostException;
import java.time.LocalDate;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;



@ExtendWith(MockitoExtension.class)
public class PartidaTest {

    @Mock
    private PartidaRepository repository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioPartidaService usuarioPartidaService;
    @Mock
    private UsuarioPartidaRepository usuarioPartidaRepository;
    @InjectMocks
    private PartidaService service;

    private Partida partida;

    private PartidaDTO partidaDTO;

    private Set<UsuarioPartida> usuarioPartida;


    @BeforeEach
    public void setup() {
        partidaDTO = new PartidaDTO(1,50.0,null,5, LocalDate.of(2024,06,20), null,usuarioPartida);
        partida = new Partida(partidaDTO);
    }


    @Test
    @DisplayName("Nova partida criada com sucesso")
    public void testNewMatchCreatedSuccessfuly() {
        given(repository.save(partida)).willReturn(partida);

        Partida partidaCreated = service.criarPartida(partida);

        assertNotNull(partidaCreated);
        assertEquals("AGUARDANDO_ANFITRIAO", partidaCreated.getStatus().toString());
    }

    @Test
    @DisplayName("Cadastro de anfitrião em partida aberta")
    public void testHostInMatchCreatedSuccessfuly() {
        given(repository.save(partida)).willReturn(partida);
        given(repository.getReferenceById(1)).willReturn(partida);

        partida.setUsuarioAnfitriaoId(1);
        service.cadastrarAnfitriao(partida);

        assertNotNull(partida);
        assertEquals(1, partida.getUsuarioAnfitriaoId());
        assertEquals(PartidaStatus.ABERTA, partida.getStatus());
    }

    @Test
    @DisplayName("Partida cancelada com sucesso")
    public void testCancelMatchSuccessfuly() {
        partida.setStatus(PartidaStatus.AGUARDANDO_ANFITRIAO);
        partida.setUsuarioAnfitriaoId(1);
        given(repository.save(partida)).willReturn(partida);
        given(repository.getReferenceById(1)).willReturn(partida);

        service.cancelarPartida(partida);

        assertNotNull(partida);
        assertEquals(PartidaStatus.CANCELADA, partida.getStatus());
    }

    @Test
    @DisplayName("Partida iniciada com sucesso")
    public void testStartMatchSuccessfuly() {
        partida.setStatus(PartidaStatus.ABERTA);
        partida.setUsuarioAnfitriaoId(1);
        given(repository.save(partida)).willReturn(partida);
        given(repository.getReferenceById(1)).willReturn(partida);

        service.iniciarPartida(partida);

        assertNotNull(partida);
        assertEquals(PartidaStatus.INICIADA, partida.getStatus());
    }

    @Test
    @DisplayName("Anfitrião cancelado com sucesso")
    public void testCancelHostAttendSuccessfuly() {
        partida.setStatus(PartidaStatus.ABERTA);
        partida.setUsuarioAnfitriaoId(1);
        given(repository.save(partida)).willReturn(partida);

        service.anfitriaoCancelado(partida);

        assertNotNull(partida);
        assertEquals(PartidaStatus.CANCELADA, partida.getStatus());
    }

    @Test
    @DisplayName("Testa se anfitrião está cadastrado")
    public void testIfHostIsDefinedTrue() {
        given(repository.findByStatusAguardandoAnfitriao()).willReturn(null);

        boolean isHostDefined = service.isAnfitriaoDefinido();

        assertNotNull(partida);
        assertEquals(true, isHostDefined);
    }

    @Test
    @DisplayName("Testa se anfitrião não está cadastrado")
    public void testIfHostIsDefinedFalse() {
        given(repository.findByStatusAguardandoAnfitriao()).willReturn(partida);

        boolean isHostDefined = service.isAnfitriaoDefinido();

        assertNotNull(partida);
        assertEquals(false, isHostDefined);
    }

    @Test
    @DisplayName("Busca por partida com status ABERTA retornando-a")
    public void testFindingOpenedStatusMatchTrue() {
        partida.setStatus(PartidaStatus.ABERTA);
        given(repository.findByStatusAberta()).willReturn(partida);
        given(repository.findByStatusAguardandoAnfitriao()).willReturn(null);
        Partida openedMatch = service.buscarPorStatusAberta();

        assertNotNull(openedMatch);
        assertEquals(PartidaStatus.ABERTA, openedMatch.getStatus());
    }

    @Test
    @DisplayName("Busca por partida com status ABERTA lançando exceção")
    public void testFindingOpenedStatusMatchFalse() {
        partida.setStatus(PartidaStatus.ABERTA);
        given(repository.findByStatusAguardandoAnfitriao()).willReturn(partida);

        assertThrows(PartidaWithNoHostException.class, () -> {
            service.buscarPorStatusAberta();
        });
    }

    @Test
    @DisplayName("Finalizar partida com sucesso")
    public void testFinishMatchSuccesfully() {
        partida.setStatus(PartidaStatus.ABERTA);
        given(repository.save(partida)).willReturn(partida);

        service.finalizarPartida(partida);

        assertNotNull(partida);
        assertEquals(PartidaStatus.FINALIZADA, partida.getStatus());
    }

    @Test
    @DisplayName("Finalizar partida com status CANCELADA lançando exceção")
    public void testFinishMatchCancelledException() {
        partida.setStatus(PartidaStatus.CANCELADA);

        assertThrows(PartidaUnableToUpdateException.class, () -> {
            service.finalizarPartida(partida);
        });
    }

    @Test
    @DisplayName("Finalizar partida com status FECHADA lançando exceção")
    public void testFinishMatchClosedException() {
        partida.setStatus(PartidaStatus.FECHADA);

        assertThrows(PartidaUnableToUpdateException.class, () -> {
            service.finalizarPartida(partida);
        });
    }


    @Test
    @DisplayName("Fechar partida com sucesso")
    public void testCloseMatchSuccesfully() {
        partida.setStatus(PartidaStatus.ABERTA);
        given(repository.save(partida)).willReturn(partida);

        service.fecharPartida(partida);

        assertNotNull(partida);
        assertEquals(PartidaStatus.FECHADA, partida.getStatus());
    }

    @Test
    @DisplayName("Fechar partida com status FECHADA lançando exceção")
    public void testCloseMatchThrowClosedException() {
        partida.setStatus(PartidaStatus.FECHADA);

        assertThrows(PartidaUnableToUpdateException.class, () -> {
            service.fecharPartida(partida);
        });
    }

    @Test
    @DisplayName("Fechar partida com status CANCELADA lançando exceção")
    public void testCloseMatchThrowCancelledException() {
        partida.setStatus(PartidaStatus.CANCELADA);

        assertThrows(PartidaUnableToUpdateException.class, () -> {
            service.fecharPartida(partida);
        });
    }

}
