package mx.gob.profeco.atlas.service;

import mx.gob.profeco.atlas.domain.IdiomaCat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing IdiomaCat.
 */
public interface IdiomaCatService {

    /**
     * Save a idiomaCat.
     *
     * @param idiomaCat the entity to save
     * @return the persisted entity
     */
    IdiomaCat save(IdiomaCat idiomaCat);

    /**
     *  Get all the idiomaCats.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<IdiomaCat> findAll(Pageable pageable);

    /**
     *  Get the "id" idiomaCat.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    IdiomaCat findOne(Long id);

    /**
     *  Delete the "id" idiomaCat.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the idiomaCat corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<IdiomaCat> search(String query, Pageable pageable);
}
