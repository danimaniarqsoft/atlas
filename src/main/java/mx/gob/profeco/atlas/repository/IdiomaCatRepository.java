package mx.gob.profeco.atlas.repository;

import mx.gob.profeco.atlas.domain.IdiomaCat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the IdiomaCat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdiomaCatRepository extends JpaRepository<IdiomaCat,Long> {

}
