package mx.gob.profeco.atlas.service;

import mx.gob.profeco.atlas.domain.TemaCat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TemaCat.
 */
public interface TemaCatService {

    /**
     * Save a temaCat.
     *
     * @param temaCat the entity to save
     * @return the persisted entity
     */
    TemaCat save(TemaCat temaCat);

    /**
     *  Get all the temaCats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TemaCat> findAll(Pageable pageable);

    /**
     *  Get the "id" temaCat.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TemaCat findOne(Long id);

    /**
     *  Delete the "id" temaCat.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the temaCat corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TemaCat> search(String query, Pageable pageable);
}
