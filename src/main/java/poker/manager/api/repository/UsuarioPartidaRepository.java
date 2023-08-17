package poker.manager.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poker.manager.api.domain.UsuarioPartida;

@Repository
public interface UsuarioPartidaRepository extends JpaRepository<UsuarioPartida, Integer> {

    UsuarioPartida findFirstByIdUsuarioAndIdPartida(Integer idUsuario, Integer idPartida);
}
