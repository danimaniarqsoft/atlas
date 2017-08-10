package mx.gob.profeco.atlas.service;

import mx.gob.profeco.atlas.domain.NormaTema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing NormaTema.
 */
public interface NormaTemaService {

    /**
     * Save a normaTema.
     *
     * @param normaTema the entity to save
     * @return the persisted entity
     */
    NormaTema save(NormaTema normaTema);

    /**
     *  Get all the normaTemas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NormaTema> findAll(Pageable pageable);

    /**
     *  Get the "id" normaTema.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NormaTema findOne(Long id);

    /**
     *  Delete the "id" normaTema.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the normaTema corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NormaTema> search(String query, Pageable pageable);
}
