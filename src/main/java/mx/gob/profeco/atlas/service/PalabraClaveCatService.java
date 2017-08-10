package mx.gob.profeco.atlas.service;

import mx.gob.profeco.atlas.domain.PalabraClaveCat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PalabraClaveCat.
 */
public interface PalabraClaveCatService {

    /**
     * Save a palabraClaveCat.
     *
     * @param palabraClaveCat the entity to save
     * @return the persisted entity
     */
    PalabraClaveCat save(PalabraClaveCat palabraClaveCat);

    /**
     *  Get all the palabraClaveCats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PalabraClaveCat> findAll(Pageable pageable);

    /**
     *  Get the "id" palabraClaveCat.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PalabraClaveCat findOne(Long id);

    /**
     *  Delete the "id" palabraClaveCat.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the palabraClaveCat corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PalabraClaveCat> search(String query, Pageable pageable);
}
