package mx.gob.profeco.atlas.repository;

import mx.gob.profeco.atlas.domain.Archivo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Archivo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArchivoRepository extends JpaRepository<Archivo,Long> {

}
