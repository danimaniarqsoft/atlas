package mx.gob.profeco.atlas.repository;

import mx.gob.profeco.atlas.domain.NormaPalabraClave;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NormaPalabraClave entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NormaPalabraClaveRepository extends JpaRepository<NormaPalabraClave,Long> {

}
