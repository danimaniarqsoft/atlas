package mx.gob.profeco.atlas.repository;

import mx.gob.profeco.atlas.domain.TemaCat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TemaCat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemaCatRepository extends JpaRepository<TemaCat,Long> {

}
