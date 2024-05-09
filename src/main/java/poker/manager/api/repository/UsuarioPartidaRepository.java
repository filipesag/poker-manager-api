package poker.manager.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import poker.manager.api.domain.UsuarioPartida;

import java.util.Set;

@Repository
public interface UsuarioPartidaRepository extends JpaRepository<UsuarioPartida, Integer> {

    @Query(value = "SELECT * FROM usuario_partida u WHERE u.partida_id = ?1 and u.usuario_id = ?2", nativeQuery = true)
    UsuarioPartida buscaPorIdUsuarioAndIdPartida(Integer idPartida, Integer idUsuario);

    @Query(value = "SELECT * FROM usuario_partida u WHERE u.partida_id = ?1", nativeQuery = true)
    Set<UsuarioPartida> buscaTodosJogadoresPorPartida(Integer idPartida);

    @Query(value = "SELECT * FROM usuario_partida u WHERE u.partida_id = ?1", nativeQuery = true)
    Set<UsuarioPartida> buscaPorIdPartida(Integer idPartida);
}
