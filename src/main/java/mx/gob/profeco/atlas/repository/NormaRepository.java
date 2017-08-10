package mx.gob.profeco.atlas.repository;

import mx.gob.profeco.atlas.domain.Norma;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Norma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NormaRepository extends JpaRepository<Norma,Long> {

}
