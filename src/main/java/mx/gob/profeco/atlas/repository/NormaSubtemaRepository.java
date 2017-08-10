package mx.gob.profeco.atlas.repository;

import mx.gob.profeco.atlas.domain.NormaSubtema;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NormaSubtema entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NormaSubtemaRepository extends JpaRepository<NormaSubtema,Long> {

}
