package mx.gob.profeco.atlas.service;

import mx.gob.profeco.atlas.domain.SubtemaCat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SubtemaCat.
 */
public interface SubtemaCatService {

    /**
     * Save a subtemaCat.
     *
     * @param subtemaCat the entity to save
     * @return the persisted entity
     */
    SubtemaCat save(SubtemaCat subtemaCat);

    /**
     *  Get all the subtemaCats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SubtemaCat> findAll(Pageable pageable);

    /**
     *  Get the "id" subtemaCat.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SubtemaCat findOne(Long id);

    /**
     *  Delete the "id" subtemaCat.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the subtemaCat corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SubtemaCat> search(String query, Pageable pageable);
}
