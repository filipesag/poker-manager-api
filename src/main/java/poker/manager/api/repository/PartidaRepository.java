package poker.manager.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import poker.manager.api.domain.Partida;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Integer> {
    @Query(value = "SELECT * FROM PARTIDA p WHERE p.status = 'ABERTA'", nativeQuery = true)
    Partida findByStatusAberta();
}
