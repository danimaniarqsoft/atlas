package mx.gob.profeco.atlas.repository;

import mx.gob.profeco.atlas.domain.EstatusCat;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EstatusCat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstatusCatRepository extends JpaRepository<EstatusCat,Long> {

}
