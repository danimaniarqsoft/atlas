package mx.gob.profeco.atlas.repository;

import mx.gob.profeco.atlas.domain.NormaTema;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NormaTema entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NormaTemaRepository extends JpaRepository<NormaTema,Long> {

}
