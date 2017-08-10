package mx.gob.profeco.atlas.service;

import mx.gob.profeco.atlas.domain.NormaIdioma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing NormaIdioma.
 */
public interface NormaIdiomaService {

    /**
     * Save a normaIdioma.
     *
     * @param normaIdioma the entity to save
     * @return the persisted entity
     */
    NormaIdioma save(NormaIdioma normaIdioma);

    /**
     *  Get all the normaIdiomas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NormaIdioma> findAll(Pageable pageable);

    /**
     *  Get the "id" normaIdioma.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    NormaIdioma findOne(Long id);

    /**
     *  Delete the "id" normaIdioma.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the normaIdioma corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<NormaIdioma> search(String query, Pageable pageable);
}
