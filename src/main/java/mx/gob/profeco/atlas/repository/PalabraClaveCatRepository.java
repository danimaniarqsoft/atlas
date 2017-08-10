package mx.gob.profeco.atlas.repository;

import mx.gob.profeco.atlas.domain.PalabraClaveCat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PalabraClaveCat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PalabraClaveCatRepository extends JpaRepository<PalabraClaveCat,Long> {

}
