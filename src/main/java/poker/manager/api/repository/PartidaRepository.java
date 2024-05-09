package poker.manager.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import poker.manager.api.domain.Partida;

import java.util.List;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Integer> {
    @Query(value = "SELECT * FROM PARTIDA p WHERE p.status = 'ABERTA'", nativeQuery = true)
    List<Partida> buscaPorStatusAberta();

    @Query(value = "SELECT * FROM PARTIDA p WHERE p.status = 'INICIADA'", nativeQuery = true)
    Partida buscaPorStatusInciada();

    @Query(value = "SELECT * FROM PARTIDA p WHERE p.status = 'FINALIZADA'", nativeQuery = true)
    Partida buscaPorStatusFinalizada();

    @Query(value = "SELECT * FROM PARTIDA p WHERE p.status = 'AGUARDANDO_ANFITRIAO'", nativeQuery = true)
    Partida buscaPorStatusAguardandoAnfitriao();
}
