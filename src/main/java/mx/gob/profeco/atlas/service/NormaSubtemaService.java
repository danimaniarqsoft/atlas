package mx.gob.profeco.atlas.service;

import mx.gob.profeco.atlas.domain.NormaSubtema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing NormaSubtema.
 */
public interface NormaSubtemaService {

    /**
     * Save a normaSubtema.
     *
     * @param normaSubtema the entity to save
     * @return the persisted entity
     */
    NormaSubtema save(NormaSubtema normaSubtema);

    /**
     *  Get all the normaSubtemas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NormaSubtema> findAll(Pageable pageable);

    /**
     *  Get the "id" normaSubtema.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NormaSubtema findOne(Long id);

    /**
     *  Delete the "id" normaSubtema.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the normaSubtema corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NormaSubtema> search(String query, Pageable pageable);
}
