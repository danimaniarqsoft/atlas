package mx.gob.profeco.atlas.repository;

import mx.gob.profeco.atlas.domain.NormaIdioma;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the NormaIdioma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NormaIdiomaRepository extends JpaRepository<NormaIdioma,Long> {

}
