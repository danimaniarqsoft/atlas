package mx.gob.profeco.atlas.service;

import mx.gob.profeco.atlas.domain.EstatusCat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EstatusCat.
 */
public interface EstatusCatService {

    /**
     * Save a estatusCat.
     *
     * @param estatusCat the entity to save
     * @return the persisted entity
     */
    EstatusCat save(EstatusCat estatusCat);

    /**
     *  Get all the estatusCats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EstatusCat> findAll(Pageable pageable);

    /**
     *  Get the "id" estatusCat.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EstatusCat findOne(Long id);

    /**
     *  Delete the "id" estatusCat.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the estatusCat corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EstatusCat> search(String query, Pageable pageable);
}
