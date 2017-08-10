package mx.gob.profeco.atlas.repository;

import mx.gob.profeco.atlas.domain.SubtemaCat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SubtemaCat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubtemaCatRepository extends JpaRepository<SubtemaCat,Long> {

}
