package mx.gob.profeco.atlas.service;

import mx.gob.profeco.atlas.domain.Norma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Norma.
 */
public interface NormaService {

    /**
     * Save a norma.
     *
     * @param norma the entity to save
     * @return the persisted entity
     */
    Norma save(Norma norma);

    /**
     *  Get all the normas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Norma> findAll(Pageable pageable);

    /**
     *  Get the "id" norma.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Norma findOne(Long id);

    /**
     *  Delete the "id" norma.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the norma corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Norma> search(String query, Pageable pageable);
}
