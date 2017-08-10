package mx.gob.profeco.atlas.repository;

import mx.gob.profeco.atlas.domain.TipoNormaCat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TipoNormaCat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoNormaCatRepository extends JpaRepository<TipoNormaCat,Long> {

}
