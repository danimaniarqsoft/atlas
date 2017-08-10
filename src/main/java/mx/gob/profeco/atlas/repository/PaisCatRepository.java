package mx.gob.profeco.atlas.repository;

import mx.gob.profeco.atlas.domain.PaisCat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PaisCat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaisCatRepository extends JpaRepository<PaisCat,Long> {

}
