package poker.manager.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import poker.manager.api.domain.Partida;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Integer> {
    @Query(value = "SELECT * FROM PARTIDA p WHERE p.status = 'ABERTA'", nativeQuery = true)
    Partida findByStatusAberta();

    @Query(value = "SELECT * FROM PARTIDA p WHERE p.status = 'INICIADA'", nativeQuery = true)
    Partida findByStatusInciada();

    @Query(value = "SELECT * FROM PARTIDA p WHERE p.status = 'FINALIZADA'", nativeQuery = true)
    Partida findByStatusFinalizada();

    @Query(value = "SELECT * FROM PARTIDA p WHERE p.status = 'AGUARDANDO_ANFITRIAO'", nativeQuery = true)
    Partida findByStatusAguardandoAnfitriao();
}
